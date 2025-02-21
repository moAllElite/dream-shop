package com.niangsa.dream_shop.service.user;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.entities.User;
import com.niangsa.dream_shop.mappers.UserMapper;
import com.niangsa.dream_shop.repositories.UserRepository;
import com.niangsa.dream_shop.requests.AddUserRequest;
import com.niangsa.dream_shop.requests.AuthenticationResquest;
import com.niangsa.dream_shop.security.jwt.IJwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService  {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IJwtService jwtService;
    /**
     * check if user already exist otherwise we registered in db
     * @param request from Form
     * @return UserDto
     */

    public String registerUser( AddUserRequest request) {
        UserDetails userDetails= Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.email()))
                .map(newUserDto -> {
                    User savedUser = User.builder().email(request.email())
                            .password(request.password())
                            .lastName(request.lastName())
                            .firstName(request.firstName())
                            .build();
                 return    userRepository.save(savedUser);
                })
                .orElseThrow(
                        () -> new EntityExistsException(String.format("User already exist with provided email :%s!!!", request.email())));
            return jwtService.createToken(userDetails);
    }


    /**
     * @param request  from form
     * @return token
     */
    @Override
    public String getUserAuthenticate( AuthenticationResquest request) {
        UserDetails userDetails = loadUserByUsername(request.email());
        return jwtService.createToken(userDetails);
    }

    /**
     * search user by id
     * @param id long
     * @return UserDto
     */
    @Override
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(()-> new EntityNotFoundException("No user were found  provided id:"+id));
    }

    /**
     * @param id long
     */
    @Override
    public void delete(Long id) {
     userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, ()-> {
                    throw  new EntityNotFoundException("No user were found  provided id:"+id);
                });
    }

    /**
     * @param id user
     * @param userDto from form
     * @return User Data transfert object
     */
    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .map(existingUser->{
                    existingUser.setPassword(userDto.getPassword());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(()-> new EntityNotFoundException("No user were found  provided id:"+id));
        return userMapper.toUserDto(user);
    }

    /**
     * @return list of user
     */
    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDto)
                .toList();
    }

    /**
     * @param email from form
     * @return User informations
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("No user found with provided email:"+email));
    }
}
