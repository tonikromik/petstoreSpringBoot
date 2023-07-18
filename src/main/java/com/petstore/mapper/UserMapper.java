package com.petstore.mapper;

import com.petstore.dto.UserDto;
import com.petstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends GenericMapper<UserDto, User> {

    @Override
    @Mapping(target = "role", expression = "java(User.Role.USER)")
    User toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    void updateProperties(UserDto dto, @MappingTarget User entity);
}