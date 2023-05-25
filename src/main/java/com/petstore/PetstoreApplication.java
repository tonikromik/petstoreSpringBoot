package com.petstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class PetstoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }

    public void run(String... arg) throws Exception {
        try {
            Files.createDirectories(Paths.get("uploads"));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload.");
        }
    }
}