package com.petstore.petstoreRest.service.impl;

import com.petstore.petstoreRest.entity.Category;
import com.petstore.petstoreRest.entity.Pet;
import com.petstore.petstoreRest.repository.CategoryRepository;
import com.petstore.petstoreRest.repository.PetRepository;
import com.petstore.petstoreRest.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;

    public PetServiceImpl(PetRepository petRepository, CategoryRepository categoryRepository) {
        this.petRepository = petRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public List<Pet> findPetsByStatus(Pet.Status status) {
        return petRepository.findAllByStatus(status);
    }

    @Override
    public Pet savePet(Pet pet) {
        Category existedCategory = categoryRepository.findByName(pet.getCategory().getName().toLowerCase());
        if (existedCategory != null){
            pet.setCategory(existedCategory);
        } else {
            pet.setCategory(new Category(pet.getCategory().getName().toLowerCase()));
        }
        pet.setStatus(Pet.Status.AVAILABLE);
        petRepository.save(pet);
        return pet;
    }

    @Override
    public void updatePet(Long petId, Pet pet) {
        Pet currentPet = petRepository.findById(petId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (pet.getName() != null) currentPet.setName(pet.getName());
        if (pet.getCategory() != null) {
            Category existedCategory = categoryRepository.findByName(pet.getCategory().getName().toLowerCase());
            if (existedCategory != null){
                pet.setCategory(existedCategory);
            } else {
                pet.setCategory(new Category(pet.getCategory().getName().toLowerCase()));
            }
        }
        if (pet.getPhotoUrl() != null) currentPet.setPhotoUrl(pet.getPhotoUrl());
        if (pet.getTag() != null) currentPet.setTag(pet.getTag());
        if (pet.getStatus() != null) currentPet.setStatus(pet.getStatus());
        petRepository.save(currentPet);
    }

    @Override
    public void updatePetInTheStoreById(Long petId, String name, Pet.Status status) {
        Pet petForUpdate = petRepository.findById(petId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        petForUpdate.setName(name);
        petForUpdate.setStatus(status);
        petRepository.save(petForUpdate);
    }

    @Override
    public void deletePet(Pet pet) {
        petRepository.delete(pet);
    }
}
