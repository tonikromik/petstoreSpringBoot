package com.petstore.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", initialValue = 5)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ship_date", nullable = false)
    private LocalDateTime shipDate;

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