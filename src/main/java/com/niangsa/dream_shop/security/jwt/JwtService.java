package com.niangsa.dream_shop.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import javax.xml.crypto.AlgorithmMethod;
import java.security.AlgorithmParameters;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService implements IJwtService {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private long expirationTime;



    /**
     * @param userDetails build from User's infos
     * @return token
     */
    @Override
    public String createToken(UserDetails userDetails) {
        return generateToken(new HashMap<>() ,userDetails);
    }
    /**
     *  Generate a Bearer token
     * @param extractClaim Map of String & Object
     * @param userDetails from
     * @return token
     */

    private String generateToken(Map<String, Object> extractClaim, UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extractClaim)
                .issuer(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("roles", userDetails.getAuthorities())
                .signWith(signInKey())
                .compact();
    }

    /**
     * extract username from the token
     * @param token encrypted
     * @return username
     */
    @Override
    public String getUsernameFromToken(String token) {
       return extractClaim(token,Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(signInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * @param token generated
     * @return boolean
     */
    @Override
    public boolean isTokenValidate(String token, UserDetails userDetails) {
        try {
            String userEmail = getUsernameFromToken(token);
            return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }


    /**
     * @param token String
     * @return boolean
     */

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .verifyWith(signInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getExpiration();
        return  expirationDate.before(new Date());
    }

    /**
     * decode secret key in base 64
     * @return SecretKey
     */

    private SecretKey signInKey() {
        byte[] encodedKey = Decoders.BASE64.decode(jwtSecret);
       return Keys.hmacShaKeyFor(encodedKey);
    }
}
