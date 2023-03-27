package com.petstore.petstoreRest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class OrdersDTO {
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
