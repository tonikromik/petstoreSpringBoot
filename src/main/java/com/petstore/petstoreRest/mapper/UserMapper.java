package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    List<User> toEntities(List<UserDTO> userDTOList);

    @Mapping(target = "id", ignore = true)
    void updateProperties(UserDTO userDTO, @MappingTarget User user);
}
