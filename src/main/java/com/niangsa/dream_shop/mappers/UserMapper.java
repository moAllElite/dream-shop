package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUserEntity(UserDto userDto);
}
