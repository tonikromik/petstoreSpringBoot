package com.petstore.service.impl;

import com.petstore.mapper.UserMapper;
import com.petstore.dto.UserDTO;
import com.petstore.repository.UserRepository;
import com.petstore.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This implementation of {@link UserService} provides methods for managing user accounts.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private static final String USER_NOT_FOUND = "User with username '%s' not found.";
    private static final String USER_ALREADY_EXIST = "User with username '%s' already exists.";
    private static final String SOME_USER_ALREADY_EXIST = "Some user already exist.";

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO findByUsername(String username) {
        return userMapper.toDTO(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, username))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
        } catch (DataAccessException e) {
            throw new EntityExistsException(String.format(USER_ALREADY_EXIST, userDTO.getUserName()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(String username, UserDTO userDTO) {
        var currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, username)));

        userMapper.updateProperties(userDTO, currentUser);
        userRepository.save(currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, username))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAll(List<UserDTO> usersDTOs) {
        try {
            userRepository.saveAll(userMapper.toListEntities(usersDTOs));
        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException(SOME_USER_ALREADY_EXIST);
        }
    }
}