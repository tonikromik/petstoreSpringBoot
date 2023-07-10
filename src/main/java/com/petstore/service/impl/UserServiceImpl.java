package com.petstore.service.impl;

import com.petstore.dto.UserDTO;
import com.petstore.entity.User;
import com.petstore.mapper.UserMapper;
import com.petstore.repository.UserRepository;
import com.petstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.*;

/**
 * This implementation of {@link UserService} provides methods for managing user accounts.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public static final String USER_NOT_FOUND = "User with username '%s' not found.";
    public static final String USER_ALREADY_EXIST = "User with username '%s' or email '%s' already exists.";
    public static final String SOME_USER_ALREADY_EXIST = "Some user already exist.";
    public static final String EMPTY_USER_DTOS = "The userDTOs cannot be empty";
    private static final String USER_SAVED = "User with username '%s' saved.";
    private static final String USER_UPDATED = "User with username '%s' updated.";
    private static final String USER_DELETED = "User with username '%s' deleted.";

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public UserDTO findByUsername(String username) {
        return userMapper.toDTO(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND, username))));
    }

    /**
     * {@inheritDoc}
     */
//    @Transactional
// with Transactional I can`t handle DataIntegrityViolationException with my message
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            User saved = userRepository.save(userMapper.toEntity(userDTO));
            log.info(format(USER_SAVED, userDTO.getUserName()));
            return userMapper.toDTO(saved);
        } catch (DataAccessException e) {
            log.error(format(USER_ALREADY_EXIST, userDTO.getUserName(), userDTO.getEmail()));
            throw new DataIntegrityViolationException(format(USER_ALREADY_EXIST, userDTO.getUserName(), userDTO.getEmail()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        String username = userDTO.getUserName();
        var currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND, username)));
        userMapper.updateProperties(userDTO, currentUser);
        log.info(format(USER_UPDATED, username));
        return userMapper.toDTO(currentUser);
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
//    @Transactional
// with Transactional I can`t handle DataIntegrityViolationException with my message
    @Override
    public void createAll(List<UserDTO> usersDTOs) {
        if (usersDTOs.isEmpty()){
            throw new IllegalArgumentException(format(EMPTY_USER_DTOS));
        }
        try {
            userRepository.saveAll(userMapper.toListEntities(usersDTOs));
        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException(SOME_USER_ALREADY_EXIST);
        }
    }
}