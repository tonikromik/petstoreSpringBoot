package com.petstore.mapper;

import com.petstore.dto.UserDTO;
import com.petstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDTO, User> {

    @Mapping(target = "id", ignore = true)
    void updateProperties(UserDTO dto, @MappingTarget User entity);
}
