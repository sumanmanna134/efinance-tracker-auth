/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.user;

import com.efinance.efinance_tracker_auth.dto.UserProfileDTO;
import com.efinance.efinance_tracker_auth.entity.UserProfile;
import com.efinance.efinance_tracker_auth.repository.UserProfileRepository;
import com.efinance.efinance_tracker_auth.service.s3.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements IUserProfileService{

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserProfileRepository repository;

    @Autowired
    S3UploadService uploadService;

    @Override
    public UserProfile updateOrCreateUserProfile(UserProfileDTO dto) {
       userInfoService.isUserExistByUserId(dto.getUserId());
        try{

           UserProfile userProfile = UserProfile.builder()
                   .id(dto.getUserId())
                   .fullName(dto.getFullName())
                   .email(dto.getEmail())
                   .phone(dto.getPhone())
                   .address(dto.getAddress())
                   .gender(dto.getGender())
                   .dateOfBirth(dto.getDateOfBirth())
                   .profilePicUrl(dto.getProfilePicUrl())
                   .lastLogin(dto.getLastLogin())
                   .build();

           return repository.save(userProfile);

       }catch (Exception ex){
           throw new RuntimeException(ex.getMessage());
       }
    }

    @Override
    public UserProfile getProfileById(String userId) {
        Optional<UserProfile> user = repository.findById(userId);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }

        return user.get();
    }

    @Override
    public void uploadProfileImage(MultipartFile file, String userId) {
        UserProfile profileById = getProfileById(userId);
        if(!profileById.isDeactivated() || profileById.getId()!=null){
            try{
                String imageUrl = uploadService.uploadProfileImage(file, userId);
                profileById.setProfilePicUrl(imageUrl);
                repository.save(profileById);
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        }else{
            throw new RuntimeException("profile has been activated or banned");
        }

    }
}
