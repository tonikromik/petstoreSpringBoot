package com.petstore.controller;

import com.petstore.dto.OrdersDTO;
import com.petstore.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Validated
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/order")
    @ResponseStatus(CREATED)
    public OrdersDTO createOrder(@Valid @RequestBody OrdersDTO orderDTO) {
        return storeService.saveOrder(orderDTO);
    }

    @GetMapping("/order/{orderId}")
    public OrdersDTO findById(@PathVariable @Min(1) Long orderId) {
        return storeService.findById(orderId);
    }

    @DeleteMapping("/order/{orderId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable @Min(1) Long orderId) {
        storeService.delete(orderId);
    }
}