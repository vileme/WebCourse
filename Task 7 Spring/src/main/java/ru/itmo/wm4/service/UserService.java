package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.form.UserCredentials;
import ru.itmo.wm4.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User register(UserCredentials registerForm) {
        User user = new User();
        user.setLogin(registerForm.getLogin());
        user.setDisabled(false);
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), registerForm.getPassword());
        return user;
    }

    public User findById(Long userId) {
        if (userId != null) {
            return userRepository.findById(userId).orElse(null);
        } else return null;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void updateDisabled(String login){
       User user =  userRepository.findByLogin(login);
        if(!user.isDisabled()){
             userRepository.updateDisabled(true,user.getLogin());
        }
        else userRepository.updateDisabled(false,user.getLogin());
    }
}
