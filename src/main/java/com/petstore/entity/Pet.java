package com.petstore.entity;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "pet", cascade = {PERSIST, MERGE, REMOVE})
    private List<Order> orders;

    public enum Status {
        AVAILABLE,
        PENDING,
        SOLD
    }
}