package com.petstore.mapper;

import com.petstore.dto.TagDTO;
import com.petstore.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface TagMapper extends GenericMapper<TagDTO, Tag>{
    @Mapping(target = "pets", ignore = true)
    @Override
    TagDTO toDTO(Tag entity);
}
