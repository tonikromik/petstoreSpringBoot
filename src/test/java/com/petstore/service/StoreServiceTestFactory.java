package com.petstore.service;

import com.petstore.dto.OrderDTO;
import com.petstore.entity.Order;

import java.time.LocalDateTime;

import static com.petstore.service.PetServiceTestFactory.*;

public class StoreServiceTestFactory {

    static final Order ORDER = Order.builder()
            .id(1L)
            .pet(pet)
            .quantity(2)
            .shipDate(LocalDateTime.now())
            .status(Order.Status.APPROVED)
            .complete(true)
            .build();

    static final OrderDTO ORDER_DTO = OrderDTO.builder()
            .id(1L)
            .petDTO(PET_DTO)
            .quantity(2)
            .shipDate(LocalDateTime.now())
            .status(Order.Status.APPROVED)
            .complete(true)
            .build();
}