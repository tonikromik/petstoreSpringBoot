package com.petstore.petstoreRest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Category is required")
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @NotBlank(message = "PhotoUrl is required")
    private String photoUrl;

    @NotNull(message = "Tag is required")
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    @Enumerated(STRING)
    private Status status;

    public enum Status {
        AVAILABLE,
        PENDING,
        SOLD
    }
}
