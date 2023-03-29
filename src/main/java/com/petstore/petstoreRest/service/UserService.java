package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    User findByUsername(String username);
    Optional<User> findByUsernameWithoutException(String username);

    void updateUser(String username, User user);
    void deleteUser(User user);

    void saveAllUsers(List<User> users);
}
