package com.petstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class CategoryDTO extends BaseDTO{

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
