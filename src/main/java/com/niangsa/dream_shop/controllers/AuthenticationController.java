package com.niangsa.dream_shop.controllers;


import com.niangsa.dream_shop.requests.AddUserRequest;
import com.niangsa.dream_shop.requests.AuthenticationResquest;
import com.niangsa.dream_shop.response.AuthenticationResponse;
import com.niangsa.dream_shop.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final IUserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
           @Valid @RequestBody AddUserRequest request
    ){
        return ResponseEntity.ok().body(new AuthenticationResponse(userService.registerUser(request)));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(
           @Valid @RequestBody AuthenticationResquest resquest
    ){
        return ResponseEntity.ok().body(
                new AuthenticationResponse(userService.getUserAuthenticate(resquest))
        );

    }
}
