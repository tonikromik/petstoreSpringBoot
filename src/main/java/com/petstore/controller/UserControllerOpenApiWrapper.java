package com.petstore.controller;

import com.petstore.dto.UserDTO;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "user", description = "Operations about user")
public interface UserControllerOpenApiWrapper {
    @Operation(summary = "Create user",
            description = "This can only be done by the logged in user.",
            tags = {"user"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Created user object",
                    content = @Content(schema = @Schema(implementation = UserDTO.class),
                            examples = {@ExampleObject(value = """
                                         {
                                             "userName": "User20",
                                             "firstName": "User20",
                                             "lastName": "User20",
                                             "email": "User20@gmail.com",
                                             "password": "User20",
                                             "phone": "0123333886",
                                             "userStatus": 1
                                         }
                                    """)})),
            responses = {
                    @ApiResponse(responseCode = "400", description = "User already exists.",
                            content = @Content)
            }
    )
    @Validated(OnCreate.class)
    UserDTO createUser(@Valid @RequestBody UserDTO userDTO);

    @Operation(summary = "Get user by username",
            parameters = {
                    @Parameter(name = "username",
                            description = "The name that needs to be fetched. Use user2 for testing.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    UserDTO findUserByUsername(@PathVariable String username);

    @Operation(summary = "Updated user",
            description = "This can only be done by the logged in user.",
            tags = {"user"},
            parameters = {
                    @Parameter(name = "username", description = "name that need to be updated")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user object",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @Validated(OnUpdate.class)
    UserDTO updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDTO);

    @Operation(summary = "Delete user",
            description = "This can only be done by the logged in user.",
            tags = {"user"},
            parameters = {
                    @Parameter(name = "username", description = "The name that needs to be deleted")
            },
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    void deleteUserByUsername(@PathVariable String username);

    @Operation(summary = "Creates list of users with given input array",
            tags = {"user"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of user object",
                    content = @Content(schema = @Schema(implementation = UserDTO.class),
                            examples = {@ExampleObject(value = """
                                    [
                                         {
                                             "userName": "User10",
                                             "firstName": "User10",
                                             "lastName": "User10",
                                             "email": "User10@gmail.com",
                                             "password": "User10",
                                             "phone": "0123333886",
                                             "userStatus": 1
                                         },
                                         {
                                             "userName": "User11",
                                             "firstName": "User11",
                                             "lastName": "User11",
                                             "email": "User11@gmail.com",
                                             "password": "User11",
                                             "phone": "012355587",
                                             "userStatus": 1
                                         }
                                    ]
                                    """)})),
            responses = {
                    @ApiResponse(responseCode = "400", description = "Some user already exists.",
                            content = @Content)
            }
    )
    @Validated(OnCreate.class)
    void createUsersWithList(@Valid @RequestBody List<UserDTO> userDTOList);
}
