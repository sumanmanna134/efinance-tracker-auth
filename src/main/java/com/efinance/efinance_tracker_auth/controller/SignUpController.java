/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.controller;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.dto.UserInfoDto;
import com.efinance.efinance_tracker_auth.service.signup.SignupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class SignUpController {

    @Autowired
    SignupService signupService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody UserInfoDto userInfoDto){
        ApiResponse<?> apiResponse = signupService.SignUp(userInfoDto);
        if(apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getErrorCode()));
    }
}
