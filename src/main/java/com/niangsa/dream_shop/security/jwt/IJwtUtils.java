package com.niangsa.dream_shop.security.jwt;


import org.springframework.security.core.Authentication;

public interface IJwtUtils {
     String generateToken(Authentication authentication);
     String getUsernameFromToken(String token);
     boolean validateToken(String token);
     boolean isTokenvalid(String token);
}
