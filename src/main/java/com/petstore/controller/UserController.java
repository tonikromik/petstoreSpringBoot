package com.petstore.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.petstore.dto.UserDto;
import com.petstore.service.UserService;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController implements UserControllerOpenApiWrapper {

    public static final String USERNAME_EXCEPTION = "Parameter of 'username' is not the same as username in dto object";
    private final UserService userService;

    @Validated(OnCreate.class)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{username}", produces = APPLICATION_JSON_VALUE)
    public UserDto findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping(value = "/{username}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public UserDto updateUser(@PathVariable String username, @Valid @RequestBody UserDto userDto) {
        if (username.equals(userDto.getUserName())) {
            return userService.updateUser(userDto);
        } else {
            throw new ValidationException(USERNAME_EXCEPTION);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    @ResponseStatus(NO_CONTENT) //by default return OK
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Validated(OnCreate.class)
    @PostMapping(value = "/createWithList", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void createUsersWithList(@Valid @RequestBody List<@Valid UserDto> userDtoList) {
        userService.createAll(userDtoList);
    }
}