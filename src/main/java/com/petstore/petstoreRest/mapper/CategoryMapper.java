package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.CategoryDTO;
import com.petstore.petstoreRest.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<CategoryDTO, Category> {
    @Override
    @Mapping(target = "pets", ignore = true)
    void updateProperties(CategoryDTO dto, @MappingTarget Category entity);
}