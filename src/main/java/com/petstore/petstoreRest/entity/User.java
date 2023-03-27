package com.petstore.petstoreRest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    @NotBlank(message = "UserName is required")
    @Column(unique = true)
    private String userName;
    @NotBlank(message = "FirstName is required")
    private String firstName;
    @NotBlank(message = "LastName is required")

    private String lastName;
    @NotBlank(message = "email is required")

    private String email;
    @NotBlank(message = "Password is required")

    private String password;
    @NotBlank(message = "Phone is required")

    private String phone;
    private int userStatus;
}
