package com.petstore.controller;

import com.petstore.dto.UserDTO;
import com.petstore.service.UserService;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Validated(OnCreate.class)
    @PostMapping
    @ResponseStatus(CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{username}")
    public UserDTO findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @Validated(OnUpdate.class)
    @PutMapping("/{username}")
    public UserDTO updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(username, userDTO);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(NO_CONTENT) //by default return OK
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @Validated(OnCreate.class)
    @PostMapping("/createWithList")
    @ResponseStatus(CREATED)
    public void createUsersWithList(@Valid @RequestBody List<UserDTO> userDTOList) {
        userService.createAll(userDTOList);
    }
}