package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final IUserService userService;
    private static final HttpStatus CREATED = HttpStatus.CREATED;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.ok().body(new ApiResponse("user delete success",null));
    }

}
