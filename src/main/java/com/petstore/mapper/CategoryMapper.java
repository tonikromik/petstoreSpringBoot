package com.petstore.mapper;

import com.petstore.dto.CategoryDto;
import com.petstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<CategoryDto, Category> {
    @Mapping(target = "pets", ignore = true)
    @Override
    CategoryDto toDto(Category entity);
}