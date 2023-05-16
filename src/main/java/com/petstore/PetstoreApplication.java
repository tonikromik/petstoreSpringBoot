package com.petstore;

import com.petstore.service.PetService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetstoreApplication implements CommandLineRunner {
    @Resource
    PetService petService;

    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }

    public void run(String... arg) throws Exception {
        petService.init();
    }
}
