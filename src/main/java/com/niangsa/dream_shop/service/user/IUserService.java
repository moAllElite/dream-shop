package com.niangsa.dream_shop.service.user;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.service.IAbstractService;

public interface IUserService extends IAbstractService<UserDto> {
     UserDto createUser(UserDto userDto);
}
