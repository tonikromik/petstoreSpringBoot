package com.petstore.service;

import com.petstore.dto.OrderDto;

/**
 * Service interface for managing orders in a store.
 */
public interface StoreService {
    /**
     * Saves the specified order.
     *
     * @param orderDto {@link OrderDto} The order to be saved.
     * @return The saved order.
     */
    OrderDto saveOrder(OrderDto orderDto);

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID, or null if not found.
     */
    OrderDto findById(Long id);

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     */
    void delete(Long id);
}
