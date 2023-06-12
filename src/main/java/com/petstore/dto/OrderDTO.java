package com.petstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    private PetDTO petDTO;

    @NotNull
    private Integer quantity;

    private LocalDateTime shipDate;

    @NotBlank
    private Status status;

    @NotNull
    private Boolean complete;
}