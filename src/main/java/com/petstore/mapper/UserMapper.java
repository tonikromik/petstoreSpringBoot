package com.petstore.mapper;

import com.petstore.dto.UserDTO;
import com.petstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDTO, User> {

    @Override
    @Mapping(target = "role", expression = "java(User.Role.USER)")
    User toEntity(UserDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateProperties(UserDTO dto, @MappingTarget User entity);
}