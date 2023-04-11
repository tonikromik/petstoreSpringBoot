package com.petstore.petstoreRest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    private Long petId;

    private Integer quantity;

    @DateTimeFormat
    private String shipDate;

    @Enumerated(STRING)
    private Status status;

    private Boolean complete;

    public enum Status {
        PLACED,
        APPROVED,
        DELIVERED
    }

    public Orders(Long petId, int quantity, String shipDate, Status status, boolean complete) {
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }
}

