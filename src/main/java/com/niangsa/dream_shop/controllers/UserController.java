package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.dto.UserDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final IUserService userService;



    @PostMapping()
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return ResponseEntity.ok(new ApiResponse("User creat success",null));
    }

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
