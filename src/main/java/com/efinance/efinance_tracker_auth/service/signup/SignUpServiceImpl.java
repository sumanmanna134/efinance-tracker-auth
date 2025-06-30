/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.signup;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.dto.JWTResponse;
import com.efinance.efinance_tracker_auth.dto.UserCredentialDto;
import com.efinance.efinance_tracker_auth.entity.RefreshToken;
import com.efinance.efinance_tracker_auth.exception.UserAlreadyExistException;
import com.efinance.efinance_tracker_auth.service.user.UserDetailServiceImpl;
import com.efinance.efinance_tracker_auth.service.jwt.JwtManager;
import com.efinance.efinance_tracker_auth.service.token.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignupService {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtManager jwtManager;
    @Override
    public ApiResponse<?> SignUp(UserCredentialDto userInfoDto) {
        boolean isSignUpSuccess = userDetailService.signUp(userInfoDto);
        if(!isSignUpSuccess){
            throw new UserAlreadyExistException("User Already Exist!");
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());

        String jwtToken = jwtManager.generateToken(userInfoDto.getUsername());

        return ApiResponse.success(JWTResponse.builder()
                .accessToken(jwtToken)
                .token(refreshToken.getRefreshToken())
                .build(), "User Registered Successfully");

    }
}
