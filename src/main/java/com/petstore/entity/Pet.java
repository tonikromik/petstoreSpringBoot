package com.petstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ElementCollection
    @CollectionTable(name = "pet_urls", joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"))
    @Column(name = "photo_url")
    private Set<String> photoUrls = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany
    @JoinTable(
            name = "pets_tags",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Enumerated(STRING)
    @Column(name = "pet_status", nullable = false)
    private Status status;

    public enum Status {
        AVAILABLE,
        PENDING,
        SOLD
    }
}