package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;

import java.util.Arrays;
import java.util.List;

public class UserServiceTestFactory {

    public static final User user = User.builder()
            .id(1L)
            .userName("user")
            .firstName("first")
            .lastName("last")
            .email("email@gmail.com")
            .password("password")
            .phone("+380970000001")
            .userStatus(1)
            .build();

    public static final User user2 = User.builder()
            .id(2L)
            .userName("user2")
            .firstName("first2")
            .lastName("last2")
            .email("email2@gmail.com")
            .password("password2")
            .phone("+380970000002")
            .userStatus(1)
            .build();
    public static final UserDTO userDTO = UserDTO.builder()
            .id(1L)
            .userName("user")
            .firstName("first")
            .lastName("last")
            .email("email@gmail.com")
            .password("password")
            .phone("+380970000001")
            .userStatus(1)
            .build();

    public static final UserDTO userDTO2 = UserDTO.builder()
            .id(2L)
            .userName("user2")
            .firstName("first2")
            .lastName("last2")
            .email("email2@gmail.com")
            .password("password2")
            .phone("+380970000002")
            .userStatus(1)
            .build();
    public static final List<User> users = Arrays.asList(user, user2);
    public static final List<UserDTO> userDTOs = Arrays.asList(userDTO, userDTO2);
}
