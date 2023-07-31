package com.petstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petstore.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.tags LEFT JOIN FETCH p.category LEFT JOIN FETCH p.photoUrls "
            + "WHERE p.id = :id")
    Optional<Pet> findById(@Param("id") Long id);

    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.tags LEFT JOIN FETCH p.category LEFT JOIN FETCH p.photoUrls "
            + "WHERE p.status = :status")
    List<Pet> findAllByStatus(@Param("status") Pet.Status status);
}