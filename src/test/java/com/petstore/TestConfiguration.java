package com.petstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public WebTestClient webTestClient() {
        return MockMvcWebTestClient.bindToApplicationContext(this.context)
                .build();
    }
}
