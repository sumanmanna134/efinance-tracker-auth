/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.repository;

import com.efinance.efinance_tracker_auth.entity.UserCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<UserCredential, Long> {

    Optional<UserCredential> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<UserCredential> findByUserId(String userId);
}
