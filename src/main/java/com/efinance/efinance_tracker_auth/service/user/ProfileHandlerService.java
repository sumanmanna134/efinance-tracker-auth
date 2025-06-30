/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.user;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.dto.UserProfileDTO;
import com.efinance.efinance_tracker_auth.entity.UserCredential;
import com.efinance.efinance_tracker_auth.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;

@Service
public class ProfileHandlerService {
    @Autowired
    IUserProfileService userProfileService;

    @Autowired
    UserInfoService userInfoService;

    public ApiResponse<?> createProfile(UserProfileDTO profileDTO) {
        UserProfile createUserProfile = userProfileService.updateOrCreateUserProfile(profileDTO);
        if(createUserProfile==null){
            throw new RuntimeException("Error happens during User data modification");
        }

        return ApiResponse.success(createUserProfile, "Profile created!");

    }

    public ApiResponse<?> getProfile(String userId){
        LinkedHashMap<String, Object> userProfile = new LinkedHashMap<>();
        UserProfile profileById = userProfileService.getProfileById(userId);
        userProfile.put("profile", profileById);
        return ApiResponse.success(userProfile, "Profile fetched");
    }

    public ApiResponse<?> uploadProfileImage(MultipartFile file, String userId) {
        userProfileService.uploadProfileImage(file, userId);
        return getProfile(userId);
    }


}
