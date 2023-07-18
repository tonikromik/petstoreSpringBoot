package com.petstore.service;

import com.petstore.dto.UserDto;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * The UserService interface defines methods for managing user accounts.
 */
public interface UserService {
    /**
     * Saves the user details in the database.
     *
     * @param userDto {@link UserDto} the data transfer object containing the user details
     * @return the saved user data transfer object
     */
    UserDto createUser(UserDto userDto);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found
     * @return the data transfer object containing the user details, or null if not found
     */
    UserDto findByUsername(String username);

    /**
     * Updates the details of an existing user.
     *
     * @param userDto  the data transfer object containing the updated user details
     */
    UserDto updateUser(UserDto userDto);

    /**
     * Deletes a user with the given username.
     *
     * @param username the username of the user to be deleted
     */
    void deleteUser(String username);

    /**
     * Saves a list of user details in the database.
     *
     * @param usersDto the list of data transfer objects containing the user details
     */
    void createAll(List<UserDto> usersDto) throws DataAccessException;
}
