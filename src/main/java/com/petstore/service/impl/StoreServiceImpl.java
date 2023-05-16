package com.petstore.service.impl;

import com.petstore.entity.Orders;
import com.petstore.repository.StoreRepository;
import com.petstore.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link StoreService} interface for managing orders in a store.
 */
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository ordersRepository;

    private static final String ORDER_NOT_FOUND = "Order with id '%d' not found.";

    /**
     * {@inheritDoc}
     */
    @Override
    public Orders findById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ORDER_NOT_FOUND, id)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Orders saveOrder(Orders order) {
        return ordersRepository.save(order);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Long id) {
        var order = ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ORDER_NOT_FOUND, id)));
        ordersRepository.delete(order);
    }
}
