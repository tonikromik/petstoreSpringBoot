package com.petstore.petstoreRest.controller;

import org.springframework.validation.ObjectError;
import com.petstore.petstoreRest.entity.Pet;
import com.petstore.petstoreRest.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    public Pet findById(@PathVariable Long petId) {
        checkId(petId);
        Pet pet = petService.findById(petId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return pet;
    }

    @RequestMapping(value = "/pet/findByStatus", method = RequestMethod.GET)
    public ResponseEntity<List<Pet>> findByStatus(@RequestParam("status") String status) {
        try {
            Pet.Status petStatus = Pet.Status.valueOf(status.toUpperCase());
            List<Pet> pets = petService.findPetsByStatus(petStatus);
            if (pets.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(pets);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/pet", method = RequestMethod.POST)
    public ResponseEntity<Pet> addNewPet(@Valid @RequestBody Pet pet, BindingResult bindingResult) {
        checkIfValid(bindingResult);
        Pet savedPet = petService.savePet(pet);
        Long savedPetId = savedPet.getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{petId}")
                .buildAndExpand(savedPetId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePetById(@PathVariable Long petId,
                                              @Valid @RequestBody Pet pet,
                                              BindingResult bindingResult) {
        checkId(petId);
        checkIfValid(bindingResult);
        checkIfEmpty(petService.findById(petId));
        petService.updatePet(petId, pet);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.POST)
    public ResponseEntity<Void> updatePetWithFormDataById(@PathVariable Long petId,
                                                          @RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "status", required = false) String status) {
        try {
            Pet.Status petStatus = Pet.Status.valueOf(status.toUpperCase());
            petService.updatePetInTheStoreById(petId, name, petStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(405).eTag("Invalid input").build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePetById(@PathVariable Long petId) {
        checkId(petId);
        Pet petForDelete = petService.findById(petId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        petService.deletePet(petForDelete);
        return ResponseEntity.noContent().build();
    }

    // 400 Invalid ID supplied
    private void checkId(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID supplied");
        }
    }

    //  404 Not found
    private void checkIfEmpty(Optional<Pet> optionalPet) {
        if (optionalPet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //  405 Validation exception
    private void checkIfValid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, errors);
        }
    }
}
