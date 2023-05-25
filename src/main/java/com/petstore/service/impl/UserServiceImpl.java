package com.petstore.service.impl;

import com.petstore.dto.UserDTO;
import com.petstore.mapper.UserMapper;
import com.petstore.repository.UserRepository;
import com.petstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.*;

/**
 * This implementation of {@link UserService} provides methods for managing user accounts.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private static final String USER_NOT_FOUND = "User with username '%s' not found.";
    private static final String USER_ALREADY_EXIST = "User with username '%s' or email '%s' already exists.";
    private static final String SOME_USER_ALREADY_EXIST = "Some user already exist.";

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
            return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException(format(USER_ALREADY_EXIST, userDTO.getUserName(), userDTO.getEmail()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public UserDTO updateUser(String username, UserDTO userDTO) {
        var currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_NOT_FOUND, username)));

        userMapper.updateProperties(userDTO, currentUser);
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
    }

    /**
     * {@inheritDoc}
     */
//    @Transactional
// with Transactional I can`t handle DataIntegrityViolationException with my message
    @Override
    public void createAll(List<UserDTO> usersDTOs) {
        try {
            userRepository.saveAll(userMapper.toListEntities(usersDTOs));
        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException(SOME_USER_ALREADY_EXIST);
        }
    }
}