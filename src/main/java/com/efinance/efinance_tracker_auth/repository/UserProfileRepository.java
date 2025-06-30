/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.repository;

import com.efinance.efinance_tracker_auth.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

//    Optional<UserProfile> findByEmailAndPhone(String )

    boolean existsByEmailAndPhone(String email, String phone);
}
