package com.petstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "tags_seq")
    @SequenceGenerator(name = "tags_seq", sequenceName = "tags_seq", initialValue = 13)
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String name;

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST)
    private List<Pet> pets = new ArrayList<>();

    public void assignPet(Pet pet) {
        pets.add(pet);
        pet.getTags().add(this);
    }
}
