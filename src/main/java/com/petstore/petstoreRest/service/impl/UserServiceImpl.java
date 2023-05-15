package com.petstore.petstoreRest.service.impl;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
//        } catch (DataAccessException e) {
        } catch (PersistenceException e) {
            throw new EntityExistsException(String.format(USER_ALREADY_EXIST, userDTO.getUserName()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
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
    @Transactional
    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, username))));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createAll(List<UserDTO> usersDTOs) {
        try {
            userRepository.saveAll(userMapper.toListEntities(usersDTOs));
//        } catch (JDBCException e) {
//            throw new JDBCException(SOME_USER_ALREADY_EXIST, null);
//        }
        }catch (DataAccessException e) {
            throw new DataIntegrityViolationException(SOME_USER_ALREADY_EXIST);
        }
    }
}