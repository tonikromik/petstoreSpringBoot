package com.petstore.petstoreRest.dto;

import com.petstore.petstoreRest.entity.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetDTO extends BaseDTO {

    private Long id;

    @NotBlank(message = "Pet name is required")
    private String name;

    @NotNull(message = "Category is required")
    private CategoryDTO category;

    @NotNull(message = "Urls are required")
    private List<String> photoUrls = new ArrayList<>();

    private List<TagDTO> tags = new ArrayList<>();

    private Pet.Status status = Pet.Status.AVAILABLE;
}
