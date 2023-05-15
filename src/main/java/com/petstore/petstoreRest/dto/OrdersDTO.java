package com.petstore.petstoreRest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class OrdersDTO extends BaseDTO{

    private Long id;

    @NotNull
    private Long petId;

    @NotNull
    private Integer quantity;

    @NotBlank
    @DateTimeFormat
    private String shipDate;

    @NotBlank
    private String status;

    @NotNull
    private Boolean complete;
}
