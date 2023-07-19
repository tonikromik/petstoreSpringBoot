package com.petstore.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petstore.entity.Pet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Tag")
public class TagDto extends BaseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @Builder.Default
    private List<Pet> pets = new ArrayList<>();
}
