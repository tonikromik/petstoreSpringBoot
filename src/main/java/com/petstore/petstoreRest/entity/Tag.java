package com.petstore.petstoreRest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
