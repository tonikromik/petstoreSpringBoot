package com.petstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO extends BaseDTO {

    private Long id;
    @NotNull
    private PetDTO pet;

    @NotNull
    private Integer quantity;

    private LocalDateTime shipDate;

    @NotBlank
    private String status;

    @NotNull
    private Boolean complete;
}