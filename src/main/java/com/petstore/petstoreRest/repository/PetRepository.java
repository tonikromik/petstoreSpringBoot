package com.petstore.petstoreRest.repository;

import com.petstore.petstoreRest.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByStatus(Pet.Status status);
}
