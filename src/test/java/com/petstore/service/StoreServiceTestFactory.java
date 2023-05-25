package com.petstore.service;

import com.petstore.dto.OrdersDTO;
import com.petstore.entity.Orders;

import java.time.LocalDateTime;

import static com.petstore.service.PetServiceTestFactory.*;

public class StoreServiceTestFactory {

    public static final Orders ORDER = Orders.builder()
            .id(1L)
            .pet(pet)
            .quantity(2)
            .shipDate(LocalDateTime.now())
            .status(Orders.Status.APPROVED)
            .complete(true)
            .build();

    public static final OrdersDTO ORDERS_DTO = OrdersDTO.builder()
            .id(1L)
            .petDTO(PET_DTO)
            .quantity(2)
            .shipDate(LocalDateTime.now())
            .status("APPROVED")
            .complete(true)
            .build();
}