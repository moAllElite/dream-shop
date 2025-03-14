package com.niangsa.dream_shop.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final IUserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long userId){
        return ResponseEntity.ok(new ApiResponse("User delete success",null));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id , @RequestBody UserDto userDto){
        return ResponseEntity.ok(
                new ApiResponse("User update success",userService.update(id,userDto)));
    }

}
