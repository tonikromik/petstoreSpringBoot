package com.petstore.controller;

import com.petstore.dto.PetDTO;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "pet", description = "Everything about your Pets")
public interface PetControllerOpenApiWrapper {
    @Operation(summary = "Add a new pet to the store",
            tags = {"pet"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Pet object that needs to be added to the store",
                    content = @Content(schema = @Schema(implementation = PetDTO.class),
                            examples = {@ExampleObject(value = """
                                    {
                                    "name": "filip",
                                    "category": {
                                        "id":"1",
                                        "name": "cat"
                                    },
                                    "photoUrls": ["url1", "url2", "url3", "url4"],
                                    "tags": [
                                        {"id": "1",
                                        "name": "aggressive"},
                                        {"id": "3",
                                        "name": "evil"},
                                        {"id": "4",
                                        "name": "wild"}
                                        ]
                                    }
                                    """)})),
            responses = {@ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)}
    )
    @Validated(OnCreate.class)
    PetDTO addPet(@Valid @RequestBody PetDTO petDTO);

    @Operation(summary = "Update an existing pet",
            tags = {"pet"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Pet object that needs to be added to the store",
                    content = @Content(schema = @Schema(implementation = PetDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                    "id": "8",
                                    "name": "Oskar",
                                    "category": {
                                                "id": 2,
                                                "name": "dog"
                                                },
                                    "photoUrls": ["url100500", "url123", "url112"],
                                    "tags": [
                                            {
                                                "id": 1,
                                                "name": "aggressive"
                                            },
                                            {
                                                "id": 3,
                                                "name": "evil"
                                            }
                                        ],
                                        "status": "PENDING"
                                    }
                                    """))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = PetDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content),
                    @ApiResponse(responseCode = "405", description = "Validation exception", content = @Content)
            }
    )
    @Validated(OnUpdate.class)
    PetDTO updateExistedPet(@Valid @RequestBody PetDTO petDTO);

    List<PetDTO> findByStatus(@RequestParam("status") String status);

    PetDTO findById(@PathVariable @Min(1) Long petId);

    PetDTO updatePetWithFormDataById(@PathVariable Long petId,
                                     @RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "status", required = false) String status);

    void deletePetById(@PathVariable Long petId);

    void uploadImage(@PathVariable Long petId, @RequestParam("file") MultipartFile image);
}
