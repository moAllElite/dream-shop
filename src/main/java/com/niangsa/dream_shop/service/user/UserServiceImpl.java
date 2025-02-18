package com.niangsa.dream_shop.service.user;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.entities.User;
import com.niangsa.dream_shop.mappers.UserMapper;
import com.niangsa.dream_shop.repositories.UserRepository;
import com.niangsa.dream_shop.security.user.ShopUserDetails;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService  {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    /**
     * check if user already exist otherwise we registered in db
     * @param userDto from Form
     * @return UserDto
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        return Optional.of(userDto)
                .filter(user -> !userRepository.existsByEmail(user.getEmail()))
                .map(newUserDto ->{
                 UserDto  savedUserDto = UserDto.builder()
                            .email(userDto.getEmail())
                            .username(userDto.getUsername())
                            .password(userDto.getPassword())
                            .lastName(userDto.getLastName())
                            .firstName(userDto.getFirstName())
                            .build();
                    User user = userRepository.save(userMapper.toUserEntity(savedUserDto));
                    return  userMapper.toUserDto(user);
                } )
                .orElseThrow(()-> new EntityExistsException(String.format("User already exist with provided email :%s!!!",userDto.getEmail())));
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
     * @param email
     * @return User informations
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("No user found with provided email:"+email));
        return ShopUserDetails.buildUserDetail(user);
    }
}
