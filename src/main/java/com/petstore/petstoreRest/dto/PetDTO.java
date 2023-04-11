package com.petstore.petstoreRest.dto;

import com.petstore.petstoreRest.entity.Category;
import com.petstore.petstoreRest.entity.Pet;
import com.petstore.petstoreRest.entity.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDTO {

    private Long id;

    private String name;

    private Category category;

    private String photoUrl;

    private Tag tag;

    private Pet.Status status;
}
