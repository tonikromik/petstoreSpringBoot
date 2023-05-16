package com.petstore.controller;

import com.petstore.dto.PetDTO;
import com.petstore.service.PetService;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/pet")
@Validated
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @Validated(OnCreate.class)
    @PostMapping
    @ResponseStatus(CREATED)
    public PetDTO addPet(@Valid @RequestBody PetDTO petDTO) {
        return petService.addPet(petDTO);
    }

    @Validated(OnUpdate.class)
    @PutMapping
    public void updateExistedPet(@Valid @RequestBody PetDTO petDTO) {
        petService.updatePet(petDTO);
    }

    @GetMapping("/findByStatus")
    public List<PetDTO> findByStatus(@RequestParam("status") String status) {
        return petService.findPetsByStatus(status);
    }

    @GetMapping("/{petId}")
    public PetDTO findById(@PathVariable @Min(1) Long petId) {
        return petService.findById(petId);
    }

    @PostMapping("/{petId}")
    public void updatePetWithFormDataById(@PathVariable Long petId,
                                          @RequestParam(name = "name", required = false) String name,
                                          @RequestParam(name = "status", required = false) String status) {
        petService.updatePetInTheStoreById(petId, name, status);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(NO_CONTENT)
    public void deletePetById(@PathVariable Long petId) {
        petService.deletePet(petId);
    }

    @PostMapping("/{petId}/uploadImage")
    public void uploadImage(@PathVariable Long petId, @RequestParam("file") MultipartFile image) {
        petService.uploadImage(petId, image);
    }
}
