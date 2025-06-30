/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.user;

import com.efinance.efinance_tracker_auth.entity.UserCredential;

public interface UserInfoService {

    public UserCredential getUserByUsername(String username);

    public boolean isUserExist(String username);

    public void isUserExistByUserId(String userId);

    public UserCredential getUserByUserId(String userId);

    public void save(UserCredential userCredential);
}
