package com.petstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "pets_seq")
    @SequenceGenerator(name = "pets_seq", sequenceName = "pets_seq", initialValue = 8)
    private Long id;

    @Column(name = "pet_name", nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "pet_urls", joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"))
    @Column(name = "photo_url")
    private Set<String> photoUrls = new HashSet<>();

    @Builder.Default
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

    @OneToMany(mappedBy = "pet", cascade = {PERSIST, MERGE})
    private List<Order> orders;
    public enum Status {
        AVAILABLE,
        PENDING,
        SOLD
    }
}