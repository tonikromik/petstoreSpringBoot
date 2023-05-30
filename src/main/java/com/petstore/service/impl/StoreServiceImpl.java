package com.petstore.service.impl;

import com.petstore.dto.OrderDTO;
import com.petstore.entity.Order;
import com.petstore.mapper.OrdersMapper;
import com.petstore.repository.StoreRepository;
import com.petstore.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.*;

/**
 * Implementation of {@link StoreService} interface for managing orders in a store.
 */
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final OrdersMapper ordersMapper;

    private static final String ORDER_NOT_FOUND = "Order with id '%d' not found.";

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public OrderDTO findById(Long id) {
        Order entity = storeRepository.findAllFieldsById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(ORDER_NOT_FOUND, id)));
        return ordersMapper.toDTO(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        return ordersMapper.toDTO(storeRepository.save(ordersMapper.toEntity(orderDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Long id) {
        var order = storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(ORDER_NOT_FOUND, id)));
        storeRepository.delete(order);
    }
}