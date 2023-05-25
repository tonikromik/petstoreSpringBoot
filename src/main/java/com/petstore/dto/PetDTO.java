package com.petstore.dto;

import com.petstore.entity.Pet;
import com.petstore.validation.OnCreate;
import com.petstore.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO extends BaseDTO {

    @Null(groups = OnCreate.class, message = "id must be null")
    @NotNull(groups = OnUpdate.class, message = "id is required")
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Pet name is required")
    private String name;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Category is required")
    private CategoryDTO category;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Urls are required")
    private Set<String> photoUrls = new HashSet<>();

    private Set<TagDTO> tags = new HashSet<>();

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class, message = "Status is required")
    private Pet.Status status;
}