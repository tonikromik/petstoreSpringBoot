package com.petstore.service.impl;

import com.petstore.dto.OrdersDTO;
import com.petstore.entity.Orders;
import com.petstore.mapper.OrdersMapper;
import com.petstore.repository.StoreRepository;
import com.petstore.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

/**
 * Implementation of {@link StoreService} interface for managing orders in a store.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final OrdersMapper ordersMapper;

    private static final String ORDER_NOT_FOUND = "Order with id '%d' not found.";
    private static final String ORDER_SAVED = "Order with id '%d' saved.";
    private static final String ORDER_DELETED = "Order with id '%d' deleted.";

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public OrdersDTO findById(Long id) {
        Orders entity = storeRepository.findAllFieldsById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(ORDER_NOT_FOUND, id)));
        return ordersMapper.toDTO(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public OrdersDTO saveOrder(OrdersDTO ordersDTO) {
        Orders saved = storeRepository.save(ordersMapper.toEntity(ordersDTO));
        log.info(format(ORDER_SAVED, saved.getId()));
        return ordersMapper.toDTO(saved);
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