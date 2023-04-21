package com.petstore.petstoreRest.service.impl;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.exception.UserAlreadyExistException;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO findByUsername(String username) {
        return userMapper.toDTO(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username \"" + username + "\" not found")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
        } catch (DataAccessException e) {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(String username, UserDTO userDTO) {
        var currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username \"" + username + "\" not found"));

        userMapper.updateProperties(userDTO, currentUser);
        userRepository.save(currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username \"" + username + "\" not found")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAll(List<UserDTO> usersDTOs) {
        try {
            userRepository.saveAll(userMapper.toEntities(usersDTOs));
        } catch (DataAccessException e) {
            throw new UserAlreadyExistException("Some user already exist");
        }
    }
}