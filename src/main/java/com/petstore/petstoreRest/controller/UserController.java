package com.petstore.petstoreRest.controller;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        checkIfValid(bindingResult);
        User savedUser = userService.saveUser(UserMapper.INSTANCE.toUser(userDTO));
//        Long savedUserId = savedUser.getId();
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{userId}")
//                .buildAndExpand(savedUserId)
//                .toUri();
//        return ResponseEntity.created(location).build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping( "/user/createWithList")
    public ResponseEntity<Void> createUserWithList(@Valid @RequestBody List<UserDTO> userDTOList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<User> users = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            users.add(UserMapper.INSTANCE.toUser(userDTO));
        }
        userService.saveAllUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{username}")
    public UserDTO findUserByUsername(@PathVariable String username) {
        if (isValidUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username supplied");
        }
        return UserMapper.INSTANCE.toDTO(userService.findByUsername(username));
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<Void> updateUserByUsername(@PathVariable String username,
                                                     @RequestBody UserDTO userDTO,
                                                     BindingResult bindingResult) {
        if (isValidUsername(username) && !username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username supplied");
        }
        checkIfValid(bindingResult);
        userService.updateUser(username, UserMapper.INSTANCE.toUser(userDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{username}")
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
