/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.user;

import com.efinance.efinance_tracker_auth.dto.UserProfileDTO;
import com.efinance.efinance_tracker_auth.entity.UserProfile;
import org.springframework.web.multipart.MultipartFile;

public interface IUserProfileService {
    public UserProfile updateOrCreateUserProfile(UserProfileDTO dto);

    public UserProfile getProfileById(String userId);

    public void uploadProfileImage(MultipartFile file, String userId);
}
