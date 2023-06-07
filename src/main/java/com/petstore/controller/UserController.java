package com.petstore.controller;

import com.petstore.dto.UserDTO;
import com.petstore.service.UserService;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController implements UserControllerOpenApiWrapper {

    private final UserService userService;

    @Validated(OnCreate.class)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{username}", produces = APPLICATION_JSON_VALUE)
    public UserDTO findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping(value = "/{username}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public UserDTO updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(username, userDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    @ResponseStatus(NO_CONTENT) //by default return OK
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUser(username);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Validated(OnCreate.class)
    @PostMapping(value = "/createWithList", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void createUsersWithList(@Valid @RequestBody List<UserDTO> userDTOList) {
        userService.createAll(userDTOList);
    }
}