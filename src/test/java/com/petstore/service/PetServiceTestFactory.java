package com.petstore.service;

import com.petstore.dto.CategoryDTO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.TagDTO;
import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.Tag;

import java.util.*;
public class PetServiceTestFactory {
    private static final Category CATEGORY = Category.builder()
            .id(1L)
            .name("cat")
            .build();
    private static Set<String> photos = new HashSet<>(Arrays.asList("url1", "url2"));
    private static Tag tag = Tag.builder()
            .id(1L)
            .name("wild")
            .pets(new ArrayList<>())
            .build();
    private static Tag tag1 = Tag.builder()
            .id(2L)
            .name("aggressive")
            .pets(new ArrayList<>())
            .build();
    private static Set<Tag> tags = new HashSet<>(Arrays.asList(tag, tag1));
    public static Pet pet = Pet.builder()
            .id(1L)
            .name("Pet")
            .category(CATEGORY)
            .photoUrls(photos)
            .tags(tags)
            .status(Pet.Status.AVAILABLE)
            .build();

    private static final CategoryDTO CATEGORY_DTO = CategoryDTO.builder()
            .id(1L)
            .name("cat")
            .build();
    private static final TagDTO TAG_DTO = TagDTO.builder()
            .id(1L)
            .name("wild")
            .build();
    private static final TagDTO TAG_DTO1 = TagDTO.builder()
            .id(2L)
            .name("aggressive")
            .build();
    public static final Set<TagDTO> TAG_DTOS = Set.of(TAG_DTO, TAG_DTO1);
    public static final PetDTO PET_DTO = PetDTO.builder()
            .id(1L)
            .name("Pet")
            .category(CATEGORY_DTO)
            .photoUrls(photos)
            .tags(TAG_DTOS)
            .status(Pet.Status.AVAILABLE)
            .build();
    public static final PetDTO PET_DTO_UPDATED = PetDTO.builder()
            .id(1L)
            .name("Alisa")
            .category(CATEGORY_DTO)
            .photoUrls(photos)
            .tags(TAG_DTOS)
            .status(Pet.Status.PENDING)
            .build();

    public static final List<Pet> PETS = List.of(pet);
    public static final List<PetDTO> PET_DTOS = List.of(PET_DTO);
}