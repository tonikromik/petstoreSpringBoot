package com.petstore.petstoreRest.controller;

import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        checkIfValid(bindingResult);
        User savedUser = userService.saveUser(user);
        Long savedUserId = savedUser.getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(savedUserId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/user/createWithList", method = RequestMethod.POST)
    public ResponseEntity<Void> createUserWithList(@Valid @RequestBody List<@Valid User> userList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.saveAllUsers(userList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping("/user/{username}")
    public User findUserByUsername(@PathVariable String username) {
        if (isValidUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username supplied");
        }
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUserByUsername(@PathVariable String username,
                                                     @RequestBody User user,
                                                     BindingResult bindingResult) {
        if (isValidUsername(username) && !username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username supplied");
        }
        checkIfValid(bindingResult);
        userService.findByUsername(username);
        userService.updateUser(username, user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        if (isValidUsername(username) && !username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username supplied");
        }
        User existUser = userService.findByUsername(username);
        userService.deleteUser(existUser);
        return ResponseEntity.noContent().build();
    }

    private void checkIfValid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, errors);
        }
    }

    private boolean isValidUsername(String username) {
        return !username.matches("^[a-zA-Z0-9]+$");
    }
}
