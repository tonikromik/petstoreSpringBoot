package com.petstore.controller;

import com.petstore.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerOpenApiWrapper {
    private final AuthenticationServiceImpl authService;

    @PostMapping("/login")
    public void generateToken(HttpServletResponse response) throws ServletException, IOException {
        authService.generateToken(response);
    }
}