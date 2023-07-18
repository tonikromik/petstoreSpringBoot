package com.petstore.mapper;

import com.petstore.dto.TagDto;
import com.petstore.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface TagMapper extends GenericMapper<TagDto, Tag> {
    @Mapping(target = "pets", ignore = true)
    @Override
    TagDto toDto(Tag entity);
}
