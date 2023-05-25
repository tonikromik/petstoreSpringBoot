package com.petstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.*;

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

    @OneToMany(mappedBy = "category", cascade = {PERSIST, MERGE})
    private List<Pet> pets = new ArrayList<>();
}