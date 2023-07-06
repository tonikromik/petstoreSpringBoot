package com.petstore.controller;

import com.petstore.dto.PetDTO;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static com.petstore.testdatafactory.PetTestFactory.TEST_PET_DTO_FOR_CREATE;
import static com.petstore.testdatafactory.PetTestFactory.TEST_PET_DTO_UPDATED;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@DirtiesContext
@TestInstance(PER_CLASS)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:application-test.yml")
public class PetControllerTest {

    @Autowired
    private WebServiceTestClient serviceTestClient;
    private PetDTO createdPetDTO;

    @Test
    @Order(1)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldCreatePetWhenAuthorizedAsAdmin() {
        createdPetDTO = serviceTestClient.addPet(TEST_PET_DTO_FOR_CREATE)
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PetDTO.class)
                .consumeWith(response ->
                        assertThat(requireNonNull(response.getResponseBody()).getId()).isEqualTo(8L))
                .returnResult().getResponseBody();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectPetCreatingWhenUnauthorizedAsUser() {
        serviceTestClient.addPet(TEST_PET_DTO_FOR_CREATE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    public void shouldRejectPetCreatingWhenUnauthorized() {
        serviceTestClient.addPet(TEST_PET_DTO_FOR_CREATE)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldRejectPetCreatingForNotValidPetWhenAuthorizedAsAdmin() {
        var petDTO = new PetDTO();
        serviceTestClient.addPet(petDTO)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(5)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldUpdateExistedPetWhenAuthorizedAsAdmin() {
        serviceTestClient.updateExistedPet(TEST_PET_DTO_UPDATED)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PetDTO.class)
                .consumeWith(response -> {
                    var received = response.getResponseBody();
                    equals(received, TEST_PET_DTO_UPDATED);
                });
    }

    @Test
    @Order(6)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectExistedPetUpdatingWhenAuthorizedAsUser() {
        serviceTestClient.updateExistedPet(TEST_PET_DTO_UPDATED)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(7)
    public void shouldRejectExistedPetUpdatingWhenUnauthorized() {
        serviceTestClient.updateExistedPet(TEST_PET_DTO_UPDATED)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(8)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldRejectExistedPetUpdatingWhenAuthorizedAsAdminAndInvalidPetDTO() {
        var petDTO = new PetDTO();
        serviceTestClient.updateExistedPet(petDTO)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(9)
    public void shouldFindByStatus() {
        var status = "pending";
        serviceTestClient.findPetByStatus(status)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(PetDTO.class).hasSize(2);
    }

    @Test
    @Order(10)
    public void shouldReturnBadRequestWhenTryToFindPetWithInvalidStatus() {
        var status = "invalid";
        serviceTestClient.findPetByStatus(status)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(11)
    public void shouldFindById() {
        var id = createdPetDTO.getId();
        serviceTestClient.findPetById(id)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PetDTO.class)
                .consumeWith(response -> {
                    var received = response.getResponseBody();
                    equals(received, createdPetDTO);
                });
    }

    @Test
    @Order(12)
    public void shouldReturnNotFoundWhenTryToFindPetWhichNotExist() {
        var id = 100L;
        serviceTestClient.findPetById(id)
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    public void shouldReturnBadRequestWhenTryToFindPetWithInvalidId() {
        var id = 0L;
        serviceTestClient.findPetById(id)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(14)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldUpdatePetWithFormDataByIdWhenAuthorizedAsAdmin() {
        var id = 1L;
        var name = "UpdatedPet";
        var status = "pending";
        serviceTestClient.updatePetWithFormDataById(id, name, status)
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(PetDTO.class)
                .consumeWith(responce -> {
                    var received = responce.getResponseBody();
                    assertThat(requireNonNull(received).getId()).isEqualTo(id);
                    assertThat(received.getName()).isEqualTo(name);
                    assertThat(received.getStatus().toString()).isEqualTo(status.toUpperCase());
                });
    }

    @Test
    @Order(15)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectUpdatingPetWithFormDataByIdWhenAuthorizedAsUser() {
        var id = 1L;
        var name = "UpdatedPet";
        var status = "pending";
        serviceTestClient.updatePetWithFormDataById(id, name, status)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(16)
    public void shouldRejectUpdatingPetWithFormDataByIdWhenUnauthorized() {
        var id = 1L;
        var name = "UpdatedPet";
        var status = "pending";
        serviceTestClient.updatePetWithFormDataById(id, name, status)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(17)
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldRejectDeletingPetByIdWhenAuthorizedAsUser() {
        var id = 7L;
        serviceTestClient.deletePetById(id)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(18)
    public void shouldRejectDeletingPetByIdWhenUnauthorized() {
        var id = 1L;
        serviceTestClient.deletePetById(id)
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(19)
    @WithMockUser(username = "user", authorities = "ROLE_ADMIN")
    public void shouldDeletePetByIdWhenAuthorizedAsAdmin() {
        var id = createdPetDTO.getId();
        serviceTestClient.deletePetById(id)
                .expectStatus().isNoContent();
    }

    private void equals(PetDTO received, @NotNull PetDTO expected) {
        assertThat(received).isNotNull();
        assertThat(received.getId()).isEqualTo(expected.getId());
        assertThat(received.getName()).isEqualTo(expected.getName());
        assertThat(received.getCategory().getId()).isEqualTo(expected.getCategory().getId());
        assertThat(received.getStatus()).isEqualTo(expected.getStatus());
        assertThat(received.getTags().size()).isEqualTo(expected.getTags().size());
        assertThat(received.getPhotoUrls().size()).isEqualTo(expected.getPhotoUrls().size());
    }
}
