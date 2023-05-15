package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.impl.UserServiceImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.petstore.petstoreRest.service.UserServiceTestFactory.*;
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

    @Test
    public void findByUsername_ReturnUserDTO() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(USER));
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO);

        UserDTO returnedUserDTO = userServiceImpl.findByUsername("user");

        assertNotNull(returnedUserDTO);
        assertEquals(returnedUserDTO, USER_DTO);
    }

    @Test
    public void findByUsername_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenThrow(
                new EntityNotFoundException("User with username 'user' not found."));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userServiceImpl.findByUsername("user"));

        assertEquals("User with username 'user' not found.", exception.getMessage());
    }

    @Test
    public void createUser_ReturnUserDTO() {
        when(userRepository.save(any(User.class))).thenReturn(USER);
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(USER);

        UserDTO returnedUserDTO = userServiceImpl.createUser(USER_DTO);

        assertNotNull(returnedUserDTO);
        assertEquals(USER_DTO, returnedUserDTO);
        verify(userRepository).save(USER);
    }


    @Test
    public void createUser_ThrowEntityExistsException() {
        when(userRepository.save(any(User.class))).thenThrow(new EntityExistsException());
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(USER);

        assertThrows(EntityExistsException.class, () -> userServiceImpl.createUser(USER_DTO));
    }

    @Test
    public void updateUser_ReturnVoid() {
        when(userRepository.findByUserName(eq("user"))).thenReturn(Optional.of(USER));
        when(userRepository.save(any(User.class))).thenReturn(USER);

        userServiceImpl.updateUser("user", USER_DTO);

        verify(userRepository).save(USER);
    }

    @Test
    public void updateUser_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.updateUser(anyString(), USER_DTO));
    }

    @Test
    public void deleteUser_ReturnVoid() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(USER));
        doNothing().when(userRepository).delete(USER);

        assertAll(() -> userServiceImpl.deleteUser("user"));
        verify(userRepository).delete(USER);
    }

    @Test
    public void deleteUser_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.deleteUser(anyString()));
    }

    @Test
    public void createAll_ReturnVoid() {
        when(userRepository.saveAll(anyList())).thenReturn(USERS);
        when(userMapper.toListEntities(USER_DTOS)).thenReturn(USERS);

        userServiceImpl.createAll(USER_DTOS);

        verify(userRepository).saveAll(USERS);
    }

    @Test
    public void saveAllUsers_ThrowEntityExistsException() {
        when(userRepository.saveAll(anyList())).thenThrow(new EntityExistsException());

        assertThrows(EntityExistsException.class, () -> userServiceImpl.createAll(USER_DTOS));
    }
}
