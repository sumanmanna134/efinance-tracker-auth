/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.controller;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.dto.UserProfileDTO;
import com.efinance.efinance_tracker_auth.service.user.ProfileHandlerService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    ProfileHandlerService profileHandlerService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createProfile(@Valid @RequestBody UserProfileDTO userProfileDTO){
        ApiResponse<?> profile = profileHandlerService.createProfile(userProfileDTO);
        if(profile.isSuccess()){
            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.status(profile.getErrorCode()).body(profile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getProfile(@PathVariable String userId){
        if(userId.isEmpty()){
            throw new ValidationException("user id required!");
        }
        ApiResponse<?> profile = profileHandlerService.getProfile(userId);
        return getApiResponseResponseEntity(profile);
    }

    @PutMapping("/upload-profile-pic")
    public ResponseEntity<ApiResponse<?>> uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId){
        if(userId.isEmpty()){
            throw new ValidationException("user id required!");
        }
        ApiResponse<?> profile = profileHandlerService.uploadProfileImage(file, userId);
        return getApiResponseResponseEntity(profile);
    }

    private static ResponseEntity<ApiResponse<?>> getApiResponseResponseEntity(ApiResponse<?> profile) {
        if(profile.isSuccess()){
            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.status(profile.getErrorCode()).body(profile);
    }


}
