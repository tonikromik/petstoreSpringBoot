package com.petstore.petstoreRest.repository;

import com.petstore.petstoreRest.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.tags LEFT JOIN FETCH p.category WHERE p.id = :id")
    Optional<Pet> findById(@Param("id") Long id);
    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.tags LEFT JOIN FETCH p.category WHERE p.status = :status")
    List<Pet> findAllByStatus(@Param("status") Pet.Status status);
}
