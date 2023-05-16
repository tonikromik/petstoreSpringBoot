package com.petstore.repository;

import com.petstore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Orders, Long> {
}
