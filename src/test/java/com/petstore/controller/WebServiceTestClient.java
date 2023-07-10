package com.petstore.controller;

import com.petstore.dto.OrderDTO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static java.lang.String.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class WebServiceTestClient {

    @Autowired
    private WebTestClient webTestClient;

    public WebTestClient.ResponseSpec saveOrder(OrderDTO orderDTO) {
        return this.webTestClient.post()
                .uri("/store/order")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(orderDTO)
                .exchange();
    }

    public WebTestClient.ResponseSpec findOrderById(Long id) {
        return this.webTestClient.get()
                .uri("/store/order/" + id)
                .exchange();
    }

    public WebTestClient.ResponseSpec deleteOrderById(Long id) {
        return this.webTestClient.delete()
                .uri("/store/order/" + id)
                .exchange();
    }

    public WebTestClient.ResponseSpec createUser(UserDTO userDTO) {
        return this.webTestClient.post()
                .uri("/user")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(userDTO)
                .exchange();
    }

    public WebTestClient.ResponseSpec findUserByUsername(String username) {
        return this.webTestClient.get()
                .uri("/user/" + username)
                .exchange();
    }

    public WebTestClient.ResponseSpec updateUser(String username, UserDTO userDTO) {

        return this.webTestClient.put()
                .uri("/user/" + username)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(userDTO)
                .exchange();
    }

    public WebTestClient.ResponseSpec deleteUserByUsername(String username) {
        return this.webTestClient.delete()
                .uri("/user/" + username)
                .exchange();
    }

    public WebTestClient.ResponseSpec createUsersWithList(List<UserDTO> userDTOList) {
        return this.webTestClient.post()
                .uri("/user/createWithList")
                .contentType(APPLICATION_JSON)
                .bodyValue(userDTOList)
                .exchange();
    }

    public WebTestClient.ResponseSpec addPet(PetDTO petDTO) {
        return this.webTestClient.post()
                .uri("/pet")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(petDTO)
                .exchange();
    }

    public WebTestClient.ResponseSpec updateExistedPet(PetDTO petDTO) {
        return this.webTestClient.put()
                .uri("/pet")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(petDTO)
                .exchange();
    }

    public WebTestClient.ResponseSpec findPetByStatus(String status) {
        return this.webTestClient.get()
                .uri(format("/pet/findByStatus?status=%s", status))
                .accept(APPLICATION_JSON)
                .exchange();
    }

    public WebTestClient.ResponseSpec findPetById(Long id) {
        return this.webTestClient.get()
                .uri("/pet/" + id)
                .accept(APPLICATION_JSON)
                .exchange();
    }

    public WebTestClient.ResponseSpec updatePetWithFormDataById(Long id, String name, String status) {
        return this.webTestClient.post()
                .uri(format("/pet/%d?name=%s&status=%s", id, name, status))
                .accept(APPLICATION_JSON)
                .exchange();
    }

    public WebTestClient.ResponseSpec deletePetById(Long id) {
        return this.webTestClient.delete()
                .uri("/pet/" + id)
                .exchange();
    }
}