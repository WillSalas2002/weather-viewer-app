package com.will.mapper;

import com.will.dto.UserDto;
import com.will.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    User toUser(UserDto userDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    UserDto toUserDto(User user);
}
