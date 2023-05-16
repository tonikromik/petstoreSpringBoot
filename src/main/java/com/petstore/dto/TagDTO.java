package com.petstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagDTO extends BaseDTO{

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
