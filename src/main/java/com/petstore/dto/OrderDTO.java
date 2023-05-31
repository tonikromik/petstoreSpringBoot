package com.petstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
    private String status;

    @NotNull
    private Boolean complete;
}