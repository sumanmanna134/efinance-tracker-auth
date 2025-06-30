/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.token;

import com.efinance.efinance_tracker_auth.entity.RefreshToken;
import com.efinance.efinance_tracker_auth.entity.UserCredential;
import com.efinance.efinance_tracker_auth.exception.RefreshTokenExpiredException;
import com.efinance.efinance_tracker_auth.repository.RefreshTokenRepository;
import com.efinance.efinance_tracker_auth.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(String username){
        UserCredential user = userInfoService.getUserByUsername(username);

        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(user)
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshTokenExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);

            throw new RefreshTokenExpiredException("Refresh token Expired, please login with your valid credentials");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByRefreshToken(token);
    }




}
