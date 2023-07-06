package com.petstore.controller;

import com.petstore.dto.UserDTO;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.ArrayList;

import static com.petstore.controller.UserController.USERNAME_EXCEPTION;
import static com.petstore.testdatafactory.UserTestFactory.*;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@DirtiesContext
@TestInstance(PER_CLASS)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:application-test.yml")
public class UserControllerTest {

    @Autowired
    private WebServiceTestClient serviceTestClient;

    @Test
    @Order(1)
    public void shouldCreateUser() {
        serviceTestClient.createUser(TEST_USER_DTO_FOR_CREATE)
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(UserDTO.class)
                .consumeWith(response ->
                        assertThat(requireNonNull(response.getResponseBody()).getId()).isEqualTo(8L));
    }

    @Test
    @Order(2)
    public void shouldRejectUserCreatingForNotValidUser() {
        var invalidUserDTO = new UserDTO();
        serviceTestClient.createUser(invalidUserDTO)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    public void shouldReturnBadRequestWhenCreateUserWhichAlreadyExists() {
        serviceTestClient.createUser(TEST_USER_DTO_FOR_CREATE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldFindUserByUsernameWhenAuthorizedAsAdmin() {
        var username = "user";
        serviceTestClient.findUserByUsername(username)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(UserDTO.class)
                .consumeWith(response -> {
                    var received = response.getResponseBody();
                    equals(received, TEST_USER_DTO);
                });
    }

    @Test
    @Order(5)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectUserFindingWhenAuthorizedAsUser() {
        var username = "user";
        serviceTestClient.findUserByUsername(username)
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(6)
    public void shouldRejectUserFindingWhenUnauthorized() {
        var username = "user";
        serviceTestClient.findUserByUsername(username)
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(7)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldReturnNotFoundWhenAuthorizedAndTryFindUserWhichNotExist() {
        var username = "notExistUser";
        serviceTestClient.findUserByUsername(username)
                .expectStatus().isNotFound();
    }

    @Test
    @Order(8)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldUpdateUserWithValidUsernameWhenAuthorizedAsAdmin() {
        var username = "user";
        serviceTestClient.updateUser(username, TEST_USER_DTO_FOR_UPDATE)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(UserDTO.class)
                .consumeWith(response -> {
                    var received = response.getResponseBody();
                    equals(received, TEST_USER_DTO_FOR_UPDATE);
                });
    }

    @Test
    @Order(9)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldRejectUpdatingUserWithInvalidUsernameWhenAuthorizedAsAdmin() {
        var username = "invalid";
        EntityExchangeResult<byte[]> result = serviceTestClient.updateUser(username, TEST_USER_DTO_FOR_UPDATE)
                .expectStatus().isBadRequest()
                .expectBody().returnResult();
        var responseBody = new String(requireNonNull(result.getResponseBody()));

        assertTrue(responseBody.contains(USERNAME_EXCEPTION));
    }

    @Test
    @Order(10)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectUpdatingUserWithValidUsernameWhenAuthorizedAsUser() {
        var username = "user";
        serviceTestClient.updateUser(username, TEST_USER_DTO_FOR_UPDATE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(11)
    public void shouldRejectUpdatingUserWithValidUsernameWhenUnauthorized() {
        var username = "user";
        serviceTestClient.updateUser(username, TEST_USER_DTO_FOR_UPDATE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(12)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldRejectUpdatingUserWithValidUsernameAndInvalidUserDTOWhenAuthorizedAsAdmin() {
        var username = "user";
        var userDTO = UserDTO.builder().userName("user").build();
        serviceTestClient.updateUser(username, userDTO)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(13)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectDeletingUserWhenAuthorizedAsUser() {
        var username = "user3";
        serviceTestClient.deleteUserByUsername(username)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(14)
    public void shouldRejectDeletingUserWhenUnauthorized() {
        var username = "user3";
        serviceTestClient.deleteUserByUsername(username)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(15)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldDeleteUserWhenAuthorizedAsAdmin() {
        var username = "user7";
        serviceTestClient.deleteUserByUsername(username)
                .expectStatus().isNoContent();
    }

    @Test
    @Order(16)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldCreateUsersWithValidListWhenAuthorizedAsAdmin() {
        serviceTestClient.createUsersWithList(TEST_USER_DTO_LIST_FOR_CREATE)
                .expectStatus().isCreated();
    }

    @Test
    @Order(17)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldRejectCreatingUsersWithInvalidListWhenAuthorizedAsAdmin() {
        var userDTOList = new ArrayList<UserDTO>();
        serviceTestClient.createUsersWithList(userDTOList)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(18)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectCreatingUsersWithValidListWhenAuthorizedAsUser() {
        serviceTestClient.createUsersWithList(TEST_USER_DTO_LIST_FOR_CREATE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(19)
    public void shouldRejectCreatingUsersWithValidListWhenUnauthorized() {
        serviceTestClient.createUsersWithList(TEST_USER_DTO_LIST_FOR_CREATE)
                .expectStatus().isBadRequest();
    }

    private void equals(UserDTO received, @NotNull UserDTO expected) {
        assertThat(received).isNotNull();
        assertThat(received.getId()).isEqualTo(expected.getId());
        assertThat(received.getUserName()).isEqualTo(expected.getUserName());
        assertThat(received.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(received.getLastName()).isEqualTo(expected.getLastName());
        assertThat(received.getEmail()).isEqualTo(expected.getEmail());
        assertThat(received.getPassword()).isEqualTo(expected.getPassword());
        assertThat(received.getPhone()).isEqualTo(expected.getPhone());
        assertThat(received.getUserStatus()).isEqualTo(expected.getUserStatus());
    }
}