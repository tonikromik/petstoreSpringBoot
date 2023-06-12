package com.petstore.service;

import com.petstore.dto.UserDTO;
import com.petstore.entity.User;
import com.petstore.mapper.UserMapper;
import com.petstore.repository.UserRepository;
import com.petstore.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static com.petstore.service.UserServiceTestFactory.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final String USER_NOT_FOUND = "User with username '%s' not found.";
    private static final String USER_ALREADY_EXIST = "User with username '%s' or email '%s' already exists.";
    private static final String SOME_USER_ALREADY_EXIST = "Some user already exist.";

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

        verify(userRepository).findByUserName("user");
        assertNotNull(returnedUserDTO);
        assertEquals(returnedUserDTO, USER_DTO);
    }

    @Test
    public void findByUsername_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenThrow(
                new EntityNotFoundException(format(USER_NOT_FOUND, "user")));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userServiceImpl.findByUsername("user"));

        verify(userRepository).findByUserName("user");
        assertEquals(format(USER_NOT_FOUND, "user"), exception.getMessage());
    }

    @Test
    public void createUser_ReturnUserDTO() {
        when(userRepository.save(any(User.class))).thenReturn(USER);
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(USER);

        UserDTO returnedUserDTO = userServiceImpl.createUser(USER_DTO);

        verify(userRepository).save(USER);
        assertNotNull(returnedUserDTO);
        assertEquals(USER_DTO, returnedUserDTO);
    }


    @Test
    public void createUser_ThrowDataIntegrityViolationException() {
        when(userRepository.save(any(User.class))).thenThrow(
                new DataIntegrityViolationException(format(USER_ALREADY_EXIST, "user", "email@gmail.com")));
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(USER);

        var exception = assertThrows(DataIntegrityViolationException.class,
                () -> userServiceImpl.createUser(USER_DTO));

        verify(userRepository).save(any());
        assertEquals(format(USER_ALREADY_EXIST, "user", "email@gmail.com"), exception.getMessage());
    }

    @Test
    public void updateUser_ReturnUserDTO() {
        when(userRepository.findByUserName(eq("user"))).thenReturn(Optional.of(USER));
        when(userMapper.toDTO(USER)).thenReturn(USER_DTO2);

        UserDTO updatedUser = userServiceImpl.updateUser(USER_DTO2);

        verify(userRepository).findByUserName("user");
        verify(userMapper).updateProperties(USER_DTO2, USER);
        verify(userMapper).toDTO(USER);
        assertEquals(USER_DTO2, updatedUser);
    }

    @Test
    public void updateUser_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userServiceImpl.updateUser(USER_DTO));
        verify(userRepository).findByUserName(anyString());
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
        verify(userRepository).findByUserName(anyString());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void createAll_ReturnVoid() {
        when(userRepository.saveAll(anyList())).thenReturn(USERS);
        when(userMapper.toListEntities(USER_DTOS)).thenReturn(USERS);

        userServiceImpl.createAll(USER_DTOS);

        verify(userRepository).saveAll(USERS);
    }

    @Test
    public void createAllUsers_DataIntegrityViolationException() {
        when(userRepository.saveAll(anyList())).thenThrow(
                new DataIntegrityViolationException(format(SOME_USER_ALREADY_EXIST)));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> userServiceImpl.createAll(USER_DTOS));

        verify(userRepository).saveAll(anyList());
        assertEquals(format(SOME_USER_ALREADY_EXIST), exception.getMessage());
    }
}