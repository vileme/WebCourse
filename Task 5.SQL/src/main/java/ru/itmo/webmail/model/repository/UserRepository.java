package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(Long Id);
    User findByLogin(String login);
    User findByLoginAndPasswordSha(String login, String passwordSha);
    User findByEmail(String email);
    User findByEmailAndPasswordSha(String email, String passwordSha);
    List<User> findAll();
    void save(User user, String passwordSha,String email);
}
