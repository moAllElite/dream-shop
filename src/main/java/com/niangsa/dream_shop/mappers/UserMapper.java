package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface UserMapper {
    @Mapping(target = "id",source = "user.id")
    @Mapping(target = "firstName",source = "user.firstName")
    UserDto userToUserDto(User user);
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "firstName", source = "dto.firstName")
    User userDtotoUser(UserDto dto);
}
