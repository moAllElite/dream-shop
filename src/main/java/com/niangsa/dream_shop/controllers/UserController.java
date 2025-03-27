package com.niangsa.dream_shop.controllers;

import java.util.List;

import com.niangsa.dream_shop.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final IUserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long userId){
        return ResponseEntity.ok(new ApiResponse("User delete success",null));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id , @RequestBody UserDto userDto){
        return ResponseEntity.ok(
                new ApiResponse("User update success",userService.update(id,userDto)));
    }


}
