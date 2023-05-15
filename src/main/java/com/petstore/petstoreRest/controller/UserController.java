package com.petstore.petstoreRest.controller;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.service.UserService;
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

    @PostMapping()
    @ResponseStatus(CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{username}")
    public UserDTO findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/{username}")
    public void updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(username, userDTO);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(NO_CONTENT) //by default return OK
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @PostMapping("/createWithList")
    @ResponseStatus(CREATED)
    public void createUsersWithList(@Valid @RequestBody List<UserDTO> userDTOList) {
        userService.createAll(userDTOList);
    }
}