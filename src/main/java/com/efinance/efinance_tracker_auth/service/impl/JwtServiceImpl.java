/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.impl;

import com.efinance.efinance_tracker_auth.config.AppConfig;
import com.efinance.efinance_tracker_auth.service.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtManager {

    @Autowired
    private AppConfig config;

    @Override
    public String getJwtSecret(){
        return config.getJWT_SECRET();
    }




    @Override
     public <T> T extractClaims(String token, Function<Claims, T> functionResolver){
        final Claims allClaims = extractAllClaims(token);
        return functionResolver.apply(allClaims);
    }

    @Override
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(config.getJWT_SECRET());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    @Override
    public String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60))
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }


    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return JwtManager.super.validateToken(token, userDetails);
    }


}
