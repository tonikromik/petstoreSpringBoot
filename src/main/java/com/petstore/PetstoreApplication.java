package com.petstore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Petstore API", version = "1.0.0",
                description = "This is a sample server Petstore server."),
        servers = {
                @Server(url = "http://23.21.28.163:8080", description = "Production"),
                @Server(url = "http://localhost:8080", description = "Local")
        }
)
@SecurityScheme(
        name = "BasicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        description = """
                HTTP Basic Authentication.
                There are two roles for testing application security:\s
                ROLE_USER (login: user; password: user) and\s
                ROLE_ADMIN (login: admin; password: admin)"""
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        description = "For generating token use '/login' auth controller."
)
public class PetstoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }

    public void run(String... arg) {
        try {
            Files.createDirectories(Paths.get("uploads"));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload.");
        }
    }
}