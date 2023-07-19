package com.petstore.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petstore.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerOpenApiWrapper {
    private final AuthenticationServiceImpl authService;

    @PostMapping("/login")
    public void generateToken(HttpServletResponse response) throws ServletException, IOException {
        authService.generateToken(response);
    }
}