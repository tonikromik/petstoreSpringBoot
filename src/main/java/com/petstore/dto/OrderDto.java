package com.petstore.dto;

import static com.petstore.entity.Order.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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