package com.petstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthControllerOpenApiWrapper {
    @Operation(summary = "Login and generate token",
            description = "After logining adds bearer token to response header. We can use it for authorization in our app.",
            security = {@SecurityRequirement(name = "BasicAuth")}
    )
    void generateToken(HttpServletResponse response) throws ServletException, IOException;
}
