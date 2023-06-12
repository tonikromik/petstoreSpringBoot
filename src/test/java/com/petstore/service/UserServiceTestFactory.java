package com.petstore.service;

import com.petstore.dto.UserDTO;
import com.petstore.entity.User;

import java.util.Arrays;
import java.util.List;

public class UserServiceTestFactory {

    static final User USER = User.builder()
            .id(1L)
            .userName("user")
            .firstName("first")
            .lastName("last")
            .email("email@gmail.com")
            .password("password")
            .phone("+380970000001")
            .userStatus(1)
            .build();

    static final User USER2 = User.builder()
            .id(2L)
            .userName("user2")
            .firstName("first2")
            .lastName("last2")
            .email("email2@gmail.com")
            .password("password2")
            .phone("+380970000002")
            .userStatus(1)
            .build();
    static final UserDTO USER_DTO = UserDTO.builder()
            .id(1L)
            .userName("user")
            .firstName("first")
            .lastName("last")
            .email("email@gmail.com")
            .password("password")
            .phone("+380970000001")
            .userStatus(1)
            .build();

    static final UserDTO USER_DTO2 = UserDTO.builder()
            .id(2L)
            .userName("user2")
            .firstName("first2")
            .lastName("last2")
            .email("email2@gmail.com")
            .password("password2")
            .phone("+380970000002")
            .userStatus(1)
            .build();
    static final List<User> USERS = Arrays.asList(USER, USER2);
    static final List<UserDTO> USER_DTOS = Arrays.asList(USER_DTO, USER_DTO2);
}