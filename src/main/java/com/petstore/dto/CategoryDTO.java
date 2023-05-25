package com.petstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petstore.entity.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO extends BaseDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();
}
