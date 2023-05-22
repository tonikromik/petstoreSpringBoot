package com.petstore.service;

import com.petstore.dto.OrdersDTO;

/**
 * Service interface for managing orders in a store.
 */
public interface StoreService {
    /**
     * Saves the specified order.
     *
     * @param ordersDTO {@link OrdersDTO} The order to be saved.
     * @return The saved order.
     */
    OrdersDTO saveOrder(OrdersDTO ordersDTO);

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID, or null if not found.
     */
    OrdersDTO findById(Long id);

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     */
    void delete(Long id);
}
