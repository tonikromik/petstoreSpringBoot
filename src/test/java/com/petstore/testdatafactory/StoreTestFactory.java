package com.petstore.testdatafactory;

import com.petstore.dto.OrderDTO;
import com.petstore.entity.Order;

import java.time.LocalDateTime;

import static com.petstore.testdatafactory.PetTestFactory.*;

public class StoreTestFactory {

    public static final Order TEST_ORDER = Order.builder()
            .id(2L)
            .pet(test_pet)
            .quantity(1)
            .shipDate(LocalDateTime.parse("2023-03-27T11:06:37"))
            .status(Order.Status.PLACED)
            .complete(true)
            .build();

    public static final OrderDTO TEST_ORDER_DTO = OrderDTO.builder()
            .id(2L)
            .pet(TEST_PET_DTO)
            .quantity(1)
            .shipDate(LocalDateTime.parse("2023-03-27T11:06:37"))
            .status(Order.Status.PLACED)
            .complete(true)
            .build();

    public static final OrderDTO TEST_ORDER_DTO_FOR_SAVE = OrderDTO.builder()
            .pet(TEST_PET_DTO)
            .quantity(1)
            .shipDate(LocalDateTime.parse("2023-03-27T11:06:37"))
            .status(Order.Status.PLACED)
            .complete(true)
            .build();
}