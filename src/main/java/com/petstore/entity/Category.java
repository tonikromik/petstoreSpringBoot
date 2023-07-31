package com.petstore.entity;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.SEQUENCE;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "categories_seq")
    @SequenceGenerator(name = "categories_seq", sequenceName = "categories_seq", initialValue = 4)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category", cascade = {PERSIST, MERGE})
    private List<Pet> pets = new ArrayList<>();
}