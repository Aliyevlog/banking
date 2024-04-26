package org.project.unitech.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.project.unitech.dto.UserDTO;
import org.project.unitech.model.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "accounts", ignore = true)
    User userDTOtoUser(UserDTO userDTO);
}
