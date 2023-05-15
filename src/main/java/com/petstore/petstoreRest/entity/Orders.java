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
@Table(name = "orders")
public class Orders extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", initialValue = 5)
    private Long id;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @DateTimeFormat
    @Column(name = "ship_date", nullable = false)
    private String shipDate;

    @Enumerated(STRING)
    @Column(name = "order_status", nullable = false)
    private Status status;

    @Column(name = "complete", nullable = false)
    private Boolean complete;

    public enum Status {
        PLACED,
        APPROVED,
        DELIVERED
    }
}

