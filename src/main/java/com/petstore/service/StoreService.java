package com.petstore.service;

import com.petstore.entity.Orders;

/**
 * Service interface for managing orders in a store.
 */
public interface StoreService {
    /**
     * Saves the specified order.
     *
     * @param order The order to be saved.
     * @return The saved order.
     */
    Orders saveOrder(Orders order);

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID, or null if not found.
     */
    Orders findById(Long id);

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     */
    void delete(Long id);
}
