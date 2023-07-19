package com.petstore.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.petstore.security.CustomUserDetailsService;
import com.petstore.security.JwtService;
import com.petstore.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public void generateToken(HttpServletResponse response) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = customUserDetailsService.loadUserByUsername(username);

        String token = jwtService.generateToken(user);
        response.addHeader("Authorization", "Bearer " + token);
    }
}