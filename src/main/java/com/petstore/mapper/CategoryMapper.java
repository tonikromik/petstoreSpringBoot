package com.petstore.mapper;

import com.petstore.dto.CategoryDTO;
import com.petstore.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<CategoryDTO, Category> {
//    @Override
////    @Mapping(target = "pets", ignore = true)
//    void updateProperties(CategoryDTO dto, @MappingTarget Category entity);
}