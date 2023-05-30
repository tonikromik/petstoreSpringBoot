package com.petstore.dto;

import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User")
public class UserDTO extends BaseDTO{

    @Null(groups = OnCreate.class, message = "id must be null")
    @NotNull(groups = OnUpdate.class, message = "id is required")
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Username is required")
    private String userName;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Firstname is required")
    private String firstName;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Lastname is required")
    private String lastName;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "email is required")
    private String email;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Password is required")
    private String password;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Phone is required")
    private String phone;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Integer userStatus;
}