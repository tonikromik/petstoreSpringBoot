package com.petstore.petstoreRest.service.impl;

import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        if (!findByUsernameWithoutException(user.getUserName()).isPresent()){
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with such username already exist");
        }
    }

    @Override
    public Optional<User> findByUsernameWithoutException(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user;
    }

    @Override
    public void updateUser(String username, User user) {
        User currentUser = userRepository.findByUserName(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user.getUserName() != null) currentUser.setUserName(user.getUserName());
        if (user.getFirstName() != null) currentUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) currentUser.setLastName(user.getLastName());
        if (user.getEmail() != null) currentUser.setEmail(user.getEmail());
        if (user.getPassword() != null) currentUser.setPassword(user.getPassword());
        if (user.getPhone() != null) currentUser.setPhone(user.getPhone());
        if (user.getUserStatus() != 0) currentUser.setUserStatus(user.getUserStatus());
        userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void saveAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }
}
