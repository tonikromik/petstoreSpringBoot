package com.petstore.controller;

import com.petstore.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "store", description = "Access to Petstore orders")
public interface StoreControllerOpenApiWrapper {
    @Operation(summary = "Place an order for a pet",
            tags = {"store"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "order placed for purchasing the pet",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class),
                            examples = {@ExampleObject(value = """
                                    {
                                      "pet": {
                                            "id": 5,
                                            "name": "Cat3",
                                            "category": {
                                                "id": 1,
                                                "name": "cat"
                                                },
                                            "photoUrls": [
                                                "url51"
                                                ],
                                            "tags": [],
                                            "status": "AVAILABLE"
                                            },
                                      "quantity": 1,
                                      "status": "PLACED",
                                      "complete": true
                                    }""")})),
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid Order", content = @Content)
            },
            security = {
                    @SecurityRequirement(name = "BasicAuth"),
                    @SecurityRequirement(name = "BearerAuth")
            }
    )
    OrderDTO createOrder(@Valid @RequestBody OrderDTO orderDTO);

    @Operation(summary = "Find purchase order by ID",
            description = "For valid response try integer IDs with value >=1 and <= 5. Other values will generated exceptions",
            tags = {"store"},
            parameters = {
                    @Parameter(name = "orderId", description = "ID of order that needs to be fetched", in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = OrderDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
            },
            security = {
                    @SecurityRequirement(name = "BasicAuth"),
                    @SecurityRequirement(name = "BearerAuth")
            }
    )
    OrderDTO findById(@PathVariable @Min(1) Long orderId);

    @Operation(summary = "Delete purchase order by ID",
            description = "For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors",
            tags = {"store"},
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            },
            security = {
                    @SecurityRequirement(name = "BasicAuth"),
                    @SecurityRequirement(name = "BearerAuth")
            }
    )
    void deleteById(@PathVariable @Min(1) Long orderId);
}