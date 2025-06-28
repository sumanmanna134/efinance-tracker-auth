/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface JwtManager {
    String getJwtSecret();
    public <T> T extractClaims(String token, Function<Claims, T> functionResolver);
    Claims extractAllClaims(String token);
    SecretKey getSignKey();

    public String extractUserName(String token);

    public Date extractExpiration(String token);

    public String createToken(Map<String, Object> claims, String username);

    default String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }





    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    default boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
