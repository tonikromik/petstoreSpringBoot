package com.petstore.petstoreRest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank(message = "UserName is required")
    @Column(unique = true)
    private String userName;

//    @NotBlank(message = "FirstName is required")
    private String firstName;

//    @NotBlank(message = "LastName is required")
    private String lastName;

//    @NotBlank(message = "email is required")
    private String email;

//    @NotBlank(message = "Password is required")
    private String password;

//    @NotBlank(message = "Phone is required")
    private String phone;

    private Integer userStatus;
}
