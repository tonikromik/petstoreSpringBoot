package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        user = new User(1L, "user", "first", "last",
                "email@gmail.com", "password", "+380970000001", 1);
        userDTO = new UserDTO(1L, "user", "first", "last",
                "email@gmail.com", "password", "+380970000001", 1);
    }

    @Test
    public void findByUsername_ReturnUserDTO() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO returnedUserDTO = userServiceImpl.findByUsername("user");

        assertNotNull(returnedUserDTO);
        assertEquals(returnedUserDTO, userDTO);
    }

    @Test
    public void findByUsername_FailedTest() {
        when(userRepository.findByUserName(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> userServiceImpl.findByUsername("user"));
    }

    @Test
    public void saveUser_ReturnUserDTO() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);

        UserDTO returnedUserDTO = userServiceImpl.saveUser(userDTO);

        assertNotNull(returnedUserDTO);
        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void saveUser_FailedTest() {
        when(userRepository.save(any(User.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with such username already exist"));
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);

        assertThrows(ResponseStatusException.class, () -> userServiceImpl.saveUser(userDTO));
    }
}
