package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {
    Pet savePet(Pet pet);

    void updatePet(Long petId, Pet pet);

    Optional<Pet> findById(Long id);

    List<Pet> findPetsByStatus(Pet.Status status);

    void updatePetInTheStoreById(Long petId, String name, Pet.Status status);

    void deletePetById(Long id);

    //    void uploadImageByPetId(Long id, String url);
}