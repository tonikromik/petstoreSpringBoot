package com.petstore.petstoreRest.repository;

import com.petstore.petstoreRest.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
