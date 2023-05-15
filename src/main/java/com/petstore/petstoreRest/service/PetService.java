package com.petstore.petstoreRest.service;

import com.petstore.petstoreRest.dto.PetDTO;

import java.util.List;

/**
 * The PetService interface defines methods for managing pets.
 */
public interface PetService {
    /**
     * Adds a new pet based on the provided PetDTO object.
     *
     * @param petDTO The PetDTO object representing the pet to be added.
     * @return The PetDTO object representing the added pet.
     */
    PetDTO addPet(PetDTO petDTO);

    /**
     * Updates the pet with the provided information.
     *
     * @param petDTO The PetDTO object containing the updated information for the pet.
     */

    void updatePet(PetDTO petDTO);

    /**
     * Finds a pet by id.
     *
     * @param id the id of the pet to be found
     * @return the data transfer object containing the pet details, or null if not found
     */
    PetDTO findById(Long id);

    /**
     * Finds a pet by status.
     *
     * @param status the status of the pet to be found
     * @return the data transfer object containing the pet details, or null if not found
     */
    List<PetDTO> findPetsByStatus(String status);

    /**
     * Updates the pet in the store with the specified ID.
     *
     * @param id     The ID of the pet to update.
     * @param name   The new name for the pet.
     * @param status The new status for the pet.
     */
    void updatePetInTheStoreById(Long id, String name, String status);

    /**
     * Deletes the pet from the store with the specified ID.
     *
     * @param id The ID of the pet to delete.
     */
    void deletePet(Long id);
}