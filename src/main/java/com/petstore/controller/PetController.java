package com.petstore.controller;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dto.PetDto;
import com.petstore.service.PetService;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pet")
@Validated
@RequiredArgsConstructor
public class PetController implements PetControllerOpenApiWrapper {

    private final PetService petService;

    @PreAuthorize("hasRole('ADMIN')")
    @Validated(OnCreate.class)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public PetDto addPet(@Valid @RequestBody PetDto petDto) {
        return petService.addPet(petDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public PetDto updateExistedPet(@Valid @RequestBody PetDto petDto) {
        return petService.updatePet(petDto);
    }

    @GetMapping(value = "/findByStatus", produces = APPLICATION_JSON_VALUE)
    public List<PetDto> findByStatus(@RequestParam("status") String status) {
        return petService.findPetsByStatus(status);
    }

    @GetMapping(value = "/{petId}", produces = APPLICATION_JSON_VALUE)
    public PetDto findById(@PathVariable @Min(1) Long petId) {
        return petService.findById(petId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/{petId}", produces = APPLICATION_JSON_VALUE)
    public PetDto updatePetWithFormDataById(@PathVariable Long petId,
                                            @RequestParam(name = "name", required = false) String name,
                                            @RequestParam(name = "status", required = false) String status) {
        return petService.updatePetInTheStoreById(petId, name, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{petId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void deletePetById(@PathVariable Long petId) {
        petService.deletePet(petId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/{petId}/uploadImage", consumes = MULTIPART_FORM_DATA)
    public void uploadImage(@PathVariable Long petId, @RequestParam("file") MultipartFile image) {
        petService.uploadImage(petId, image);
    }
}