package com.petstore.repository;

import com.petstore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Orders, Long> {
    @Query("select o from Orders o " +
            "left join fetch o.pet " +
            "left join fetch o.pet.tags " +
            "left join fetch o.pet.category " +
            "left join fetch o.pet.photoUrls " +
            "left join fetch o.pet.orders " +
            "where o.id=:id")
    Optional<Orders> findAllFieldsById(@Param("id") Long id);

    Optional<Orders> findById(Long id);

    void delete(Orders order);
}