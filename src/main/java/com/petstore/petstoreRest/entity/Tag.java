package com.petstore.petstoreRest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_seq")
    @SequenceGenerator(name = "tags_seq", sequenceName = "tags_seq", initialValue = 13)
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST)
    private List<Pet> pets = new ArrayList<>();

    public void assignPet(Pet pet) {
        pets.add(pet);
        pet.getTags().add(this);
    }
}
