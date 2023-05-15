package com.petstore.petstoreRest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pets_seq")
    @SequenceGenerator(name = "pets_seq", sequenceName = "pets_seq", initialValue = 8)
    private Long id;

    @Column(name = "pet_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "pet_urls")
    private List<String> photoUrls = new ArrayList<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany
    @JoinTable(
            name = "pets_tags",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(STRING)
    @Column(name = "pet_status", nullable = false)
    private Status status = Status.AVAILABLE;

    public enum Status {
        AVAILABLE,
        PENDING,
        SOLD
    }
}