package com.petstore.service;

import com.petstore.dto.CategoryDTO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.TagDTO;
import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.Tag;

import java.util.*;
public class PetServiceTestFactory {
    static final Category CATEGORY = Category.builder()
            .id(1L)
            .name("cat")
            .build();
    static Set<String> photos = new HashSet<>(Arrays.asList("url1", "url2"));
    static Tag tag = Tag.builder()
            .id(1L)
            .name("wild")
            .pets(new ArrayList<>())
            .build();
    static Tag tag1 = Tag.builder()
            .id(2L)
            .name("aggressive")
            .pets(new ArrayList<>())
            .build();
    static Set<Tag> tags = new HashSet<>(Arrays.asList(tag, tag1));
    static Pet pet = Pet.builder()
            .id(1L)
            .name("Pet")
            .category(CATEGORY)
            .photoUrls(photos)
            .tags(tags)
            .status(Pet.Status.AVAILABLE)
            .build();

    static final CategoryDTO CATEGORY_DTO = CategoryDTO.builder()
            .id(1L)
            .name("cat")
            .build();
    static final TagDTO TAG_DTO = TagDTO.builder()
            .id(1L)
            .name("wild")
            .build();
    static final TagDTO TAG_DTO1 = TagDTO.builder()
            .id(2L)
            .name("aggressive")
            .build();
    static final Set<TagDTO> TAG_DTOS = Set.of(TAG_DTO, TAG_DTO1);
    static final PetDTO PET_DTO = PetDTO.builder()
            .id(1L)
            .name("Pet")
            .category(CATEGORY_DTO)
            .photoUrls(photos)
            .tags(TAG_DTOS)
            .status(Pet.Status.AVAILABLE)
            .build();
    static final PetDTO PET_DTO_UPDATED = PetDTO.builder()
            .id(1L)
            .name("Alisa")
            .category(CATEGORY_DTO)
            .photoUrls(photos)
            .tags(TAG_DTOS)
            .status(Pet.Status.PENDING)
            .build();

    static final List<Pet> PETS = List.of(pet);
    static final List<PetDTO> PET_DTOS = List.of(PET_DTO);
}