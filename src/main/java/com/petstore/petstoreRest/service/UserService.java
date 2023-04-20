package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO findByUsername(String username);

    void updateUser(String username, UserDTO userDTO);

    void deleteUser(String username);

    void saveAllUsers(List<UserDTO> usersDTO);
}
