package com.petstore.service.impl;

import static java.lang.String.format;

import com.petstore.dto.OrderDto;
import com.petstore.entity.Order;
import com.petstore.mapper.OrderMapper;
import com.petstore.repository.StoreRepository;
import com.petstore.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link StoreService} interface for managing orders in a store.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    public static final String ORDER_NOT_FOUND = "Order with id '%d' not found.";
    public static final String ORDER_SAVED = "Order with id '%d' saved.";
    public static final String ORDER_DELETED = "Order with id '%d' deleted.";

    private final StoreRepository storeRepository;
    private final OrderMapper orderMapper;


    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        Order entity = storeRepository.findAllFieldsById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(ORDER_NOT_FOUND, id)));
        return orderMapper.toDto(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        Order saved = storeRepository.save(orderMapper.toEntity(orderDto));
        log.info(format(ORDER_SAVED, saved.getId()));
        return orderMapper.toDto(saved);
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
        log.info(format(ORDER_DELETED, id));
    }
}