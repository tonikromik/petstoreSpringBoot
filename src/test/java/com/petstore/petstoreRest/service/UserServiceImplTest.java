package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.impl.UserServiceImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    private List<User> users;
    private List<UserDTO> userDTOs;

    @BeforeEach
    public void init() {
        user = UserServiceTestFactory.user;
        userDTO = UserServiceTestFactory.userDTO;
        users = UserServiceTestFactory.users;
        userDTOs = UserServiceTestFactory.userDTOs;
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
    public void findByUsername_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.findByUsername("user"));
    }

    @Test
    public void createUser_ReturnUserDTO() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);

        UserDTO returnedUserDTO = userServiceImpl.createUser(userDTO);

//        assertNotNull(returnedUserDTO);
        assertEquals(userDTO, returnedUserDTO);
        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void createUser_ThrowEntityExistsException() {
        when(userRepository.save(any(User.class))).thenThrow(new EntityExistsException());
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);

        assertThrows(EntityExistsException.class, () -> userServiceImpl.createUser(userDTO));
    }

    @Test
    public void updateUser_ReturnVoid() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userServiceImpl.updateUser("user", userDTO);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateUser_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.updateUser(anyString(), userDTO));
    }

    @Test
    public void deleteUser_ReturnVoid() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        assertAll(() -> userServiceImpl.deleteUser("user"));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUser_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.deleteUser(anyString()));
    }

    @Test
    public void createAll_ReturnVoid() {
        when(userRepository.saveAll(anyList())).thenReturn(users);
        when(userMapper.toEntities(userDTOs)).thenReturn(users);

        userServiceImpl.createAll(userDTOs);

        verify(userRepository, times(1)).saveAll(users);
    }

    @Test
    public void saveAllUsers_ThrowEntityExistsException() {
        when(userRepository.saveAll(anyList())).thenThrow(new EntityExistsException());

        assertThrows(EntityExistsException.class, () -> userServiceImpl.createAll(userDTOs));
    }
}
