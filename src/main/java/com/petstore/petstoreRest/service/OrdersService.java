package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.entity.Orders;

import java.util.Optional;

public interface OrdersService {
    Orders saveOrder(Orders order);

    Optional<Orders> findById(Long id);

    void delete(Orders orders);
}
