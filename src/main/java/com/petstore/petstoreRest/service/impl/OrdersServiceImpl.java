package com.petstore.petstoreRest.service.impl;

import com.petstore.petstoreRest.entity.Orders;
import com.petstore.petstoreRest.repository.OrdersRepository;
import com.petstore.petstoreRest.service.OrdersService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Orders saveOrder(Orders order) {
        return ordersRepository.save(order);
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }

    @Override
    public void delete(Orders order) {
        ordersRepository.delete(order);
    }
}
