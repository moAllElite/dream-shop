package com.niangsa.dream_shop.security.jwt;

import com.niangsa.dream_shop.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils implements IJwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    /**
     * @param authentication from Authentication
     * @return jwt
     */
    @Override
    public String generateToken(Authentication authentication) {
        ShopUserDetails principalUser = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles = principalUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .subject(principalUser.getUsername())
                .issuer(principalUser.getEmail())
                .claim("roles", roles)
                .claim("username",principalUser.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date((new Date()).getTime()+ expirationTime))
                .signWith(signInKey())
                .compact();
    }

    /**
     * extract username from the token
     * @param token encrypt
     * @return username
     */
    @Override
    public String getUsernameFromToken(String token) {
       Claims  claims = Jwts.parser()
               .verifyWith(signInKey())
               .build()
               .parseSignedClaims(jwtSecret)
               .getPayload();
        return claims.getSubject();
    }

    /**
     * @param token generated
     * @return boolean
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(signInKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException |MalformedJwtException  |IllegalArgumentException | UnsupportedJwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

    /**
     * @param token String
     * @return boolean
     */
    @Override
    public boolean isTokenvalid(String token) {
        Date expirationDate = Jwts.parser()
                .verifyWith(signInKey())
                .build()
                .parseSignedClaims(jwtSecret)
                .getPayload().getExpiration();
        Instant expirationInstant =expirationDate.toInstant();
        int i = expirationInstant.compareTo(Instant.now());
        return i != 0;
    }

    private SecretKey signInKey() {
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
