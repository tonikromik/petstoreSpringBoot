package com.petstore.testdatafactory;

import com.petstore.dto.CategoryDTO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.TagDTO;
import com.petstore.entity.Category;
import com.petstore.entity.Pet;
import com.petstore.entity.Tag;

import java.util.*;
public class PetTestFactory {
    public static final Category TEST_CATEGORY = Category.builder()
            .id(1L)
            .name("cat")
            .build();
    public static Set<String> test_photos = new HashSet<>(Arrays.asList("url1", "url2"));

    public static final Tag TEST_TAG = Tag.builder()
            .id(1L)
            .name("aggressive")
            .build();
    public static final Tag TEST_TAG3 = Tag.builder()
            .id(3L)
            .name("evil")
            .build();
    public static final Tag TEST_TAG4 = Tag.builder()
            .id(4L)
            .name("wild")
            .build();
    public static Set<Tag> test_tags = new HashSet<>(Arrays.asList(TEST_TAG, TEST_TAG3, TEST_TAG4));
    public static Pet test_pet = Pet.builder()
            .id(1L)
            .name("Cat1")
            .category(TEST_CATEGORY)
            .photoUrls(test_photos)
            .tags(test_tags)
            .status(Pet.Status.AVAILABLE)
            .build();

    public static final CategoryDTO TEST_CATEGORY_DTO = CategoryDTO.builder()
            .id(1L)
            .name("cat")
            .build();
    public static final TagDTO TEST_TAG_DTO1 = TagDTO.builder()
            .id(1L)
            .name("aggressive")
            .build();
    public static final TagDTO TEST_TAG_DTO3 = TagDTO.builder()
            .id(3L)
            .name("evil")
            .build();
    public static final TagDTO TEST_TAG_DTO4 = TagDTO.builder()
            .id(4L)
            .name("wild")
            .build();
    public static final Set<TagDTO> TEST_TAG_DTOS = Set.of(TEST_TAG_DTO1, TEST_TAG_DTO3, TEST_TAG_DTO4);
    public static final PetDTO TEST_PET_DTO = PetDTO.builder()
            .id(1L)
            .name("Cat1")
            .category(TEST_CATEGORY_DTO)
            .photoUrls(test_photos)
            .tags(TEST_TAG_DTOS)
            .status(Pet.Status.AVAILABLE)
            .build();

    public static final PetDTO TEST_PET_DTO_FOR_CREATE = PetDTO.builder()
            .name("CreatedPet")
            .category(TEST_CATEGORY_DTO)
            .photoUrls(test_photos)
            .tags(TEST_TAG_DTOS)
            .build();
    public static final PetDTO TEST_PET_DTO_UPDATED = PetDTO.builder()
            .id(1L)
            .name("Alisa")
            .category(TEST_CATEGORY_DTO)
            .photoUrls(test_photos)
            .tags(TEST_TAG_DTOS)
            .status(Pet.Status.PENDING)
            .build();

    public static final List<Pet> TEST_PETS = List.of(test_pet);
    public static final List<PetDTO> TEST_PET_DTOS = List.of(TEST_PET_DTO);
}