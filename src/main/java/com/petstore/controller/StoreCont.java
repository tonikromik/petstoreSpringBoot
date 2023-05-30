package com.petstore.controller;

import com.petstore.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "store", description = "Access to Petstore orders")
public interface StoreCont {
    @Operation(summary = "Place an order for a pet",
            tags = {"store"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "order placed for purchasing the pet"),
            responses = {@ApiResponse(responseCode = "201", description = "created",
                    content = @Content(schema =
                    @Schema(implementation = OrderDTO.class), mediaType = APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Invalid Order", content = @Content)
            })
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    OrderDTO createOrder(@Valid @RequestBody OrderDTO orderDTO);

    @Operation(summary = "Find purchase order by ID",
            description = "For valid response try integer IDs with value >=1 and <= 5. Other values will generated exceptions",
            tags = {"store"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping(produces = "application/json")
    OrderDTO findById(@Parameter(description = "ID of order that needs to be fetched") @PathVariable @Min(1) Long orderId);

    @Operation(summary = "Delete purchase order by ID",
            description = "For valid response try integer IDs with value < 1000. Anything above 1000 or non integers will generate API errors",
            tags = {"store"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    @DeleteMapping
    void deleteById(@PathVariable @Min(1) Long orderId);
}