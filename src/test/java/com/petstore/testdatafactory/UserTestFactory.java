package com.petstore.testdatafactory;

import com.petstore.dto.UserDTO;
import com.petstore.entity.User;

import java.util.Arrays;
import java.util.List;

import static com.petstore.entity.User.Role.*;

public class UserTestFactory {

    public static final User TEST_USER = User.builder()
            .id(2L)
            .userName("user")
            .firstName("seconduser")
            .lastName("user")
            .email("user2@gmail.com")
            .password("user")
            .phone("02")
            .userStatus(1)
            .role(USER)
            .build();

    public static final User TEST_USER2 = User.builder()
            .id(3L)
            .userName("admin")
            .firstName("thirduser")
            .lastName("admin")
            .email("user3@gmail.com")
            .password("admin")
            .phone("03")
            .userStatus(1)
            .role(ADMIN)
            .build();


    public static final UserDTO TEST_USER_DTO = UserDTO.builder()
            .id(2L)
            .userName("user")
            .firstName("seconduser")
            .lastName("user")
            .email("user2@gmail.com")
            .password("user")
            .phone("02")
            .userStatus(1)
            .build();

    public static final UserDTO TEST_USER_DTO2 = UserDTO.builder()
            .id(3L)
            .userName("admin")
            .firstName("thirduser")
            .lastName("admin")
            .email("user3@gmail.com")
            .password("admin")
            .phone("03")
            .userStatus(1)
            .build();

    public static final UserDTO TEST_USER_DTO_FOR_CREATE = UserDTO.builder()
            .userName("userforcreate1")
            .firstName("third")
            .lastName("third")
            .email("userforcreate1@gmail.com")
            .password("user")
            .phone("+380970000003")
            .userStatus(1)
            .build();

    public static final UserDTO TEST_USER_DTO2_FOR_CREATE = UserDTO.builder()
            .userName("userforcreate2")
            .firstName("seconduser")
            .lastName("admin")
            .email("userforcreate2@gmail.com")
            .password("admin")
            .phone("03")
            .userStatus(1)
            .build();
    public static final UserDTO TEST_USER_DTO3_FOR_CREATE = UserDTO.builder()
            .userName("userforcreate3")
            .firstName("thirduser")
            .lastName("user")
            .email("userforcreate3@gmail.com")
            .password("user")
            .phone("04")
            .userStatus(1)
            .build();

    public static final UserDTO TEST_USER_DTO_FOR_UPDATE = UserDTO.builder()
            .id(2L)
            .userName("user")
            .firstName("updated")
            .lastName("userupdated")
            .email("user2@gmail.com")
            .password("updated")
            .phone("02")
            .userStatus(1)
            .build();
    public static final List<User> TEST_USER_LIST = Arrays.asList(TEST_USER, TEST_USER2);

    public static final List<UserDTO> TEST_USER_DTO_LIST = Arrays.asList(TEST_USER_DTO, TEST_USER_DTO2);

    public static final List<UserDTO> TEST_USER_DTO_LIST_FOR_CREATE =
            Arrays.asList(TEST_USER_DTO2_FOR_CREATE, TEST_USER_DTO3_FOR_CREATE);
}