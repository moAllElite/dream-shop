package com.niangsa.dream_shop.service.user;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.requests.AddUserRequest;
import com.niangsa.dream_shop.requests.AuthenticationResquest;
import com.niangsa.dream_shop.service.IAbstractService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IAbstractService<UserDto>, UserDetailsService {

     String getUserAuthenticate(AuthenticationResquest request);

     String registerUser(AddUserRequest request);
}
