package com.petstore.petstoreRest.controller;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(CREATED)
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @GetMapping("/user/{username}")
    public UserDTO findUserByUsername(@PathVariable("username")
                                      @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Username must match any characters between a-z or A-Z ") String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/user/{username}")
    @ResponseStatus(OK)
    public void updateUser(@PathVariable("username")
                           @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Username must match any characters between a-z or A-Z ") String username,
                           @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(username, userDTO);
    }

    @DeleteMapping("/user/{username}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUserByUsername(@PathVariable("username")
                                     @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Username must match any characters between a-z or A-Z ") String username) {
        userService.deleteUser(username);
    }

    @PostMapping("/user/createWithList")
    @ResponseStatus(CREATED)
    public void createUserWithList(@RequestBody @Valid List<UserDTO> userDTOList) {
        userService.saveAllUsers(userDTOList);
    }
}
