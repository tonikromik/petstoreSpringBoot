package com.petstore.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.petstore.dto.OrderDto;
import com.petstore.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Validated
public class StoreController implements StoreControllerOpenApiWrapper {

    private final StoreService storeService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(value = "/order", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) {
        return storeService.saveOrder(orderDto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/order/{orderId}", produces = APPLICATION_JSON_VALUE)
    public OrderDto findById(@PathVariable @Min(1) Long orderId) {
        return storeService.findById(orderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/order/{orderId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable @Min(1) Long orderId) {
        storeService.delete(orderId);
    }
}