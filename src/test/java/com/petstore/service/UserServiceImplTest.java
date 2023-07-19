package com.petstore.service;

import static com.petstore.service.impl.UserServiceImpl.*;
import static com.petstore.testdatafactory.UserTestFactory.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.petstore.dto.UserDto;
import com.petstore.entity.User;
import com.petstore.mapper.UserMapper;
import com.petstore.repository.UserRepository;
import com.petstore.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void findByUsername_ReturnUserDto() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(TEST_USER));
        when(userMapper.toDto(any(User.class))).thenReturn(TEST_USER_DTO);

        var returnedUserDto = userServiceImpl.findByUsername("user");

        assertNotNull(returnedUserDto);
        assertEquals(returnedUserDto, TEST_USER_DTO);
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
    public void createUser_ReturnUserDto() {
        when(userRepository.save(any(User.class))).thenReturn(TEST_USER);
        when(userMapper.toDto(any(User.class))).thenReturn(TEST_USER_DTO);
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(TEST_USER);

        var returnedUserDto = userServiceImpl.createUser(TEST_USER_DTO);

        verify(userRepository).save(TEST_USER);
        assertNotNull(returnedUserDto);
        assertEquals(TEST_USER_DTO, returnedUserDto);
    }


    @Test
    public void createUser_ThrowDataIntegrityViolationException() {
        when(userRepository.save(any(User.class))).thenThrow(
                new DataIntegrityViolationException(format(USER_ALREADY_EXIST, "user", "user2@gmail.com")));
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(TEST_USER);

        var exception = assertThrows(DataIntegrityViolationException.class,
                () -> userServiceImpl.createUser(TEST_USER_DTO));

        verify(userRepository).save(any());
        assertEquals(format(USER_ALREADY_EXIST, "user", "user2@gmail.com"), exception.getMessage());
    }

    @Test
    public void updateUser_ReturnUserDto() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(TEST_USER));
        when(userMapper.toDto(TEST_USER)).thenReturn(TEST_USER_DTO_FOR_UPDATE);

        var updatedUser = userServiceImpl.updateUser(TEST_USER_DTO_FOR_UPDATE);

        verify(userRepository).findByUserName("user");
        verify(userMapper).updateProperties(TEST_USER_DTO_FOR_UPDATE, TEST_USER);
        assertEquals(TEST_USER_DTO_FOR_UPDATE, updatedUser);
        verify(userMapper).toDto(TEST_USER);
    }

    @Test
    public void updateUser_ThrowEntityNotFoundException() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userServiceImpl.updateUser(TEST_USER_DTO));
        verify(userRepository).findByUserName(anyString());
    }

    @Test
    public void deleteUser_ReturnVoid() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(TEST_USER));
        doNothing().when(userRepository).delete(TEST_USER);

        assertAll(() -> userServiceImpl.deleteUser("user"));
        verify(userRepository).delete(TEST_USER);
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
        when(userRepository.saveAll(anyList())).thenReturn(TEST_USER_LIST);
        when(userMapper.toListEntities(TEST_USER_DTO_LIST)).thenReturn(TEST_USER_LIST);

        userServiceImpl.createAll(TEST_USER_DTO_LIST);

        verify(userRepository).saveAll(TEST_USER_LIST);
    }

    @Test
    public void createAllUsers_DataIntegrityViolationException() {
        when(userRepository.saveAll(anyList())).thenThrow(
                new DataIntegrityViolationException(format(SOME_USER_ALREADY_EXIST)));

        var exception = assertThrows(DataIntegrityViolationException.class,
                () -> userServiceImpl.createAll(TEST_USER_DTO_LIST));

        verify(userRepository).saveAll(anyList());
        assertEquals(format(SOME_USER_ALREADY_EXIST), exception.getMessage());
    }
}