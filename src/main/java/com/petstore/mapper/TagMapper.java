package com.petstore.mapper;

import com.petstore.dto.TagDTO;
import com.petstore.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface TagMapper extends GenericMapper<TagDTO, Tag>{
//    @Override
//    void updateProperties(TagDTO dto, @MappingTarget Tag entity);
}
