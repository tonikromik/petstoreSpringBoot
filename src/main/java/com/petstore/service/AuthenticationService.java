package com.petstore.service;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService{
    void generateToken(HttpServletResponse response);
}
