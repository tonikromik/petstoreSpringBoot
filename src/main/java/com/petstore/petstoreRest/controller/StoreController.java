package com.petstore.petstoreRest.controller;

import com.petstore.petstoreRest.dto.OrdersDTO;
import com.petstore.petstoreRest.entity.Orders;
import com.petstore.petstoreRest.mapper.OrdersMapper;
import com.petstore.petstoreRest.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Validated
public class StoreController {

    private final StoreService ordersService;
    private final OrdersMapper ordersMapper;

    @PostMapping(value = "/order")
    public OrdersDTO createOrder(@Valid @RequestBody OrdersDTO orderDTO) {
        return ordersMapper.toDTO(ordersService.saveOrder(ordersMapper.toEntity(orderDTO)));
    }

    @GetMapping(value = "/order/{orderId}")
    public Orders findById(@PathVariable @Min(1) Long orderId) {
        return ordersService.findById(orderId);
    }

    @DeleteMapping(value = "/order/{orderId}")
    public void deleteById(@PathVariable @Min(1) Long orderId) {
        ordersService.delete(orderId);
    }
}
