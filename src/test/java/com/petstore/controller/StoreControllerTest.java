package com.petstore.controller;

import static com.petstore.testdatafactory.StoreTestFactory.TEST_ORDER_DTO;
import static com.petstore.testdatafactory.StoreTestFactory.TEST_ORDER_DTO_FOR_SAVE;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.petstore.dto.OrderDto;

@SpringBootTest
@DirtiesContext
@TestInstance(PER_CLASS)
@AutoConfigureTestDatabase
@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource("classpath:application-test.yml")
public class StoreControllerTest {

    @Autowired
    private WebServiceTestClient serviceTestClient;

    @Test
    @Order(1)
    @WithMockUser(username = "user", authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void shouldCreateOrderWhenAuthorized() {

        serviceTestClient.saveOrder(TEST_ORDER_DTO_FOR_SAVE)
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(OrderDto.class)
                .consumeWith(response ->
                        assertThat(requireNonNull(response.getResponseBody()).getId()).isEqualTo(5L));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "user", authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void shouldRejectOrderCreatingForNotValidOrder() {
        OrderDto invalidOrderDto = new OrderDto();
        serviceTestClient.saveOrder(invalidOrderDto)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    public void shouldRejectOrderCreatingWhenUnauthorized() {
        serviceTestClient.saveOrder(TEST_ORDER_DTO_FOR_SAVE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "user", authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void shouldFindOrderByIdWhenAuthorized() {
        var id = 2L;
        serviceTestClient.findOrderById(id)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(OrderDto.class)
                .consumeWith(response -> {
                    var received = response.getResponseBody();
                    assertThat(received).isNotNull();
                    assertThat(received.getId()).isEqualTo(TEST_ORDER_DTO.getId());
                    assertThat(received.getPet().getId()).isEqualTo(TEST_ORDER_DTO.getPet().getId());
                    assertThat(received.getQuantity()).isEqualTo(TEST_ORDER_DTO.getQuantity());
                    assertThat(received.getShipDate()).isEqualTo(TEST_ORDER_DTO.getShipDate());
                    assertThat(received.getStatus()).isEqualTo(TEST_ORDER_DTO.getStatus());
                    assertThat(received.getComplete()).isEqualTo(TEST_ORDER_DTO.getComplete());
                });
    }

    @Test
    @Order(5)
    @WithMockUser(username = "user", authorities = {"ROLE_USER", "ROLE_ADMIN"})
    public void shouldReturnBadRequestWhenTryFindOrderByInvalidIdAndAuthorized() {
        var invalidId = 0L;
        serviceTestClient.findOrderById(invalidId)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(6)
    public void shouldRejectOrderFindingWhenUnauthorized() {
        var id = 1L;
        serviceTestClient.findOrderById(id)
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(7)
    public void shouldRejectOrderDeletingWhenUnauthorized() {
        var id = 1L;
        serviceTestClient.deleteOrderById(id)
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(8)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectOrderDeletingWhenAuthorizedAsUser() {
        var id = 1L;
        serviceTestClient.deleteOrderById(id)
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(9)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldDeleteOrderByIdWhenAuthorized() {
        var id = 5L;
        serviceTestClient.deleteOrderById(id)
                .expectStatus().isNoContent();
    }
}