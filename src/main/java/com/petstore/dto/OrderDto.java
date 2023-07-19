package com.petstore.dto;

import static com.petstore.entity.Order.Status;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Order")
public class OrderDto extends BaseDto {

    private Long id;

    @Schema(name = "pet")
    @NotNull
    private PetDto pet;

    @NotNull
    private Integer quantity;

    private LocalDateTime shipDate;

    @NotNull
    private Status status;

    @NotNull
    private Boolean complete;
}