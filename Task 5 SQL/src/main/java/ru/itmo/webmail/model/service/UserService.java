package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password,String email) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (!email.contains("@") || !email.contains(".") ||
                email.charAt(0) == '@' ||
                email.lastIndexOf(".") - email.lastIndexOf("@") < 1 ||
                email.charAt(email.length() - 1) == '.' ||
                email.indexOf('@') != email.lastIndexOf('@')) {
            throw new ValidationException("Email is invalid");
        }
        if(userRepository.findByEmail(email) != null){
            throw new ValidationException("Email is already in use");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
    }

    public void register(User user, String password,String email) {
        String passwordSha = getPasswordSha(password);
        userRepository.save(user, passwordSha,email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String login, String password) throws ValidationException {
        User guest = userRepository.findByLogin(login);
        if (guest == null) {
            guest = userRepository.findByEmail(login);
        }
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login or Email is required");
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }

        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
        if (guest == null) {
            throw new ValidationException("Invalid login or Email");
        }
        if (!guest.getEmail().equals(login)) {
            if (userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password)) == null) {
                throw new ValidationException("Invalid login or password");
            }
        } else {
            if (userRepository.findByEmailAndPasswordSha(guest.getEmail(),getPasswordSha(password)) == null){
                throw new ValidationException("Invalid email or password");
            }
        }
        if (!guest.getConfirmed()) {
            throw new ValidationException("You have to verify your account first");
        }
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

    public User authorize(String login, String password) {
        if (userRepository.findByLogin(login) != null)
        return userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
        else return userRepository.findByEmailAndPasswordSha(login, getPasswordSha(password));
    }

    public User find(Long userId) {
        return userRepository.find(userId);
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }
}

