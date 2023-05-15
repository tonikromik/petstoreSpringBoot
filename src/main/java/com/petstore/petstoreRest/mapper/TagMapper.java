package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.TagDTO;
import com.petstore.petstoreRest.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface TagMapper extends GenericMapper<TagDTO, Tag>{
    @Override
    void updateProperties(TagDTO dto, @MappingTarget Tag entity);
}
