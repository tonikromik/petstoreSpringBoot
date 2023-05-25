package com.petstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import static com.petstore.entity.Orders.Status;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO extends BaseDTO {

    private Long id;
    @NotNull
    private PetDTO petDTO;

    @NotNull
    private Integer quantity;

    private LocalDateTime shipDate;

    @NotBlank
    private Status status;

    @NotNull
    private Boolean complete;
}