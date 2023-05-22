package com.petstore.mapper;

import com.petstore.dto.CategoryDTO;
import com.petstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<CategoryDTO, Category> {
    @Mapping(target = "pets", ignore = true)
    @Override
    CategoryDTO toDTO(Category entity);
}