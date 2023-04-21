package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.UserDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * The UserService interface defines methods for managing user accounts.
 */
public interface UserService {
    /**
     * Saves the user details in the database.
     *
     * @param userDTO the data transfer object containing the user details
     * @return the saved user data transfer object
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found
     * @return the data transfer object containing the user details, or null if not found
     */
    UserDTO findByUsername(String username);

    /**
     * Updates the details of an existing user with the given username.
     *
     * @param username the username of the user to be updated
     * @param userDTO  the data transfer object containing the updated user details
     */
    void updateUser(String username, UserDTO userDTO);

    /**
     * Deletes a user with the given username.
     *
     * @param username the username of the user to be deleted
     */
    void deleteUser(String username);

    /**
     * Saves a list of user details in the database.
     *
     * @param usersDTO the list of data transfer objects containing the user details
     */
    void createAll(List<UserDTO> usersDTO) throws DataAccessException;
}
