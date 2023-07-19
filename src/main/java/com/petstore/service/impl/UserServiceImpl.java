package com.petstore.service.impl;

import static java.lang.String.format;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petstore.dto.UserDto;
import com.petstore.entity.User;
import com.petstore.mapper.UserMapper;
import com.petstore.repository.UserRepository;
import com.petstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This implementation of {@link UserService} provides methods for managing user accounts.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND = "User with username '%s' not found.";
    public static final String USER_ALREADY_EXIST = "User with username '%s' or email '%s' already exists.";
    public static final String SOME_USER_ALREADY_EXIST = "Some user already exist.";
    public static final String EMPTY_USER_DTOS = "The userDTOs cannot be empty";
    private static final String USER_SAVED = "User with username '%s' saved.";
    private static final String USER_UPDATED = "User with username '%s' updated.";
    private static final String USER_DELETED = "User with username '%s' deleted.";
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public UserDto findByUsername(String username) {
        return userMapper.toDto(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND, username))));
    }

    /**
     * {@inheritDoc}
     */
    /*  @Transactional
        with Transactional I can`t handle DataIntegrityViolationException with my message */
    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            User saved = userRepository.save(userMapper.toEntity(userDto));
            log.info(format(USER_SAVED, userDto.getUserName()));
            return userMapper.toDto(saved);
        } catch (DataAccessException e) {
            log.error(format(USER_ALREADY_EXIST, userDto.getUserName(), userDto.getEmail()));
            throw new DataIntegrityViolationException(
                    format(USER_ALREADY_EXIST, userDto.getUserName(), userDto.getEmail()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto) {
        String username = userDto.getUserName();
        var currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND, username)));
        userMapper.updateProperties(userDto, currentUser);
        log.info(format(USER_UPDATED, username));
        return userMapper.toDto(currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND, username))));
        log.info(format(USER_DELETED, username));
    }

    /**
     * {@inheritDoc}
     */
    /*  @Transactional
        with Transactional I can`t handle DataIntegrityViolationException with my message */
    @Override
    public void createAll(List<UserDto> usersDto) {
        if (usersDto.isEmpty()) {
            throw new IllegalArgumentException(format(EMPTY_USER_DTOS));
        }
        try {
            userRepository.saveAll(userMapper.toListEntities(usersDto));
        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException(SOME_USER_ALREADY_EXIST);
        }
    }
}