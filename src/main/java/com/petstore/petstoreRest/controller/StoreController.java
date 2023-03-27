package com.petstore.petstoreRest.controller;

import com.petstore.petstoreRest.dto.OrdersDTO;
import com.petstore.petstoreRest.entity.Orders;
import com.petstore.petstoreRest.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class StoreController {

    private final OrdersService ordersService;

    public StoreController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @RequestMapping(value = "/store/order", method = RequestMethod.POST)
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrdersDTO orderDTO) {
        try {
            Orders.Status status = Orders.Status.valueOf(orderDTO.getStatus().toUpperCase());

            Orders order = new Orders(orderDTO.getPetId(),
                    orderDTO.getQuantity(),
                    orderDTO.getShipDate(),
                    status,
                    orderDTO.getComplete());
            Orders savedOrder = ordersService.saveOrder(order);
            Long savedOrderId = savedOrder.getId();

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{orderId}")
                    .buildAndExpand(savedOrderId)
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value: " + orderDTO.getStatus());
        }
    }

    @RequestMapping(value = "/store/order/{orderId}", method = RequestMethod.GET)
    public Orders findById(@PathVariable Long orderId) {
        checkId(orderId);
        Orders order = ordersService.findById(orderId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return order;
    }

    @RequestMapping(value = "/store/order/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable Long orderId) {
        checkId(orderId);
        Orders order = ordersService.findById(orderId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ordersService.delete(order);
        return ResponseEntity.noContent().build();
    }

    private void checkId(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID supplied");
        }
    }
}
