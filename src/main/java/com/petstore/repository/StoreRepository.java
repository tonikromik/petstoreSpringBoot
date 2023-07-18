package com.petstore.repository;

import com.petstore.entity.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o "
            + "left join fetch o.pet "
            + "left join fetch o.pet.tags "
            + "left join fetch o.pet.category "
            + "left join fetch o.pet.photoUrls "
            + "left join fetch o.pet.orders "
            + "where o.id=:id")
    Optional<Order> findAllFieldsById(@Param("id") Long id);

    Optional<Order> findById(Long id);

    void delete(Order order);
}