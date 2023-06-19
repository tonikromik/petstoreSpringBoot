package com.petstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import static com.petstore.entity.Order.Status;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Order")
public class OrderDTO extends BaseDTO {

    private Long id;

    @Schema(name = "pet")
    @NotNull
    private PetDTO pet;

    @NotNull
    private Integer quantity;

    private LocalDateTime shipDate;

    @NotNull
    private Status status;

    @NotNull
    private Boolean complete;
}