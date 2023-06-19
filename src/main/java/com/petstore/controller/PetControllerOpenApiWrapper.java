package com.petstore.controller;

import com.petstore.dto.PetDTO;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

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
            responses = {@ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)},
            security = {@SecurityRequirement(name = "BearerAuth")}
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
            },
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @Validated(OnUpdate.class)
    PetDTO updateExistedPet(@Valid @RequestBody PetDTO petDTO);

    @Operation(summary = "Finds pet by status",
            tags = {"pet"},
            parameters = {
                    @Parameter(name = "status",
                            description = "Status values that need to be considered for filter. Available values : available, pending, sold")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = PetDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid status value", content = @Content)
            }
    )
    List<PetDTO> findByStatus(@RequestParam("status") String status);

    @Operation(summary = "Updates a pet in the store with form data",
            tags = {"pet"},
            parameters = {
                    @Parameter(name = "petId",
                            description = "ID of pat that needs to be updated", required = true),
                    @Parameter(name = "name",
                            description = "Updated name of the pet"),
                    @Parameter(name = "status",
                            description = "Updated status of the pet. Available values : available, pending, sold")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = PetDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content),
                    @ApiResponse(responseCode = "405", description = "Invalid input", content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    PetDTO updatePetWithFormDataById(@PathVariable Long petId,
                                     @RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "status", required = false) String status);

    @Operation(summary = "Uploads an image",
            tags = {"pet"},
            parameters = {
                    @Parameter(name = "petId", description = "ID of the pet to upload the image for", required = true, in = ParameterIn.PATH),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "file to upload",
                    content = @Content(mediaType = MULTIPART_FORM_DATA)
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid pet ID or image file supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content),
                    @ApiResponse(responseCode = "405", description = "Validation exception", content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    void uploadImage(@PathVariable Long petId, @RequestParam("file") MultipartFile image);

    @Operation(summary = "Find pet by ID",
            description = "Returns a single pet",
            tags = {"pet"},
            parameters = {
                    @Parameter(name = "petId",
                            description = "ID of pet to return")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = PetDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content)
            }
    )
    PetDTO findById(@PathVariable @Min(1) Long petId);

    @Operation(summary = "Deletes a pet",
            tags = {"pet"},
            parameters = {
                    @Parameter(name = "petId",
                            description = "Pet id to delete")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content",
                            content = @Content(schema = @Schema(implementation = PetDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content)

            },
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    void deletePetById(@PathVariable Long petId);
}