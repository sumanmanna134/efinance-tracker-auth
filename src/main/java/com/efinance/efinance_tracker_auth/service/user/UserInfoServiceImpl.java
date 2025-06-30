/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.user;

import com.efinance.efinance_tracker_auth.entity.UserCredential;
import com.efinance.efinance_tracker_auth.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private IUserRepository IUserRepository;

    @Override
    public UserCredential getUserByUsername(String username){
        Optional<UserCredential> user = IUserRepository.findByUsername(username);
        String message = String.format("Username %s not found", username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(message);
        }
        return user.get();
    }

    @Override
    public boolean isUserExist(String username){
        return IUserRepository.existsByUsername(username);
    }

    @Override
    public void isUserExistByUserId(String userId) {
        Optional<UserCredential> byUserId = IUserRepository.findByUserId(userId);
        if(byUserId.isEmpty()){
            throw new UsernameNotFoundException("User not Found!");
        }
    }

    @Override
    public UserCredential getUserByUserId(String userId) {
        Optional<UserCredential> byUserId = IUserRepository.findByUserId(userId);
        if(byUserId.isEmpty()){
            throw new UsernameNotFoundException("User not Found!");
        }
        return byUserId.get();
    }

    @Override
    public void save(UserCredential userCredential){
        try{
           IUserRepository.save(userCredential);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


}
