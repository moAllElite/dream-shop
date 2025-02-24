package com.niangsa.dream_shop.security.jwt;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJwtService {


     String getUsernameFromToken(String token);

     <T> T extractClaim(String token, Function<Claims, T> claimResolver);



    String createToken(UserDetails userDetails);

    boolean isTokenValidate(String token, UserDetails userDetails);
}
