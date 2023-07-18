package com.petstore.testdatafactory;

import static com.petstore.testdatafactory.PetTestFactory.TEST_PET_DTO;
import static com.petstore.testdatafactory.PetTestFactory.test_pet;

import com.petstore.dto.OrderDto;
import com.petstore.entity.Order;
import java.time.LocalDateTime;


public class StoreTestFactory {

    public static final Order TEST_ORDER = Order.builder()
            .id(2L)
            .pet(test_pet)
            .quantity(1)
            .shipDate(LocalDateTime.parse("2023-03-27T11:06:37"))
            .status(Order.Status.PLACED)
            .complete(true)
            .build();

    public static final OrderDto TEST_ORDER_DTO = OrderDto.builder()
            .id(2L)
            .pet(TEST_PET_DTO)
            .quantity(1)
            .shipDate(LocalDateTime.parse("2023-03-27T11:06:37"))
            .status(Order.Status.PLACED)
            .complete(true)
            .build();

    public static final OrderDto TEST_ORDER_DTO_FOR_SAVE = OrderDto.builder()
            .pet(TEST_PET_DTO)
            .quantity(1)
            .shipDate(LocalDateTime.parse("2023-03-27T11:06:37"))
            .status(Order.Status.PLACED)
            .complete(true)
            .build();
}