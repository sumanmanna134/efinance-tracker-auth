/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.user;

import com.efinance.efinance_tracker_auth.entity.UserInfo;
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
    public UserInfo getUserByUsername(String username){
        Optional<UserInfo> user = IUserRepository.findByUsername(username);
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
    public void save(UserInfo userInfo){
        try{
           IUserRepository.save(userInfo);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


}
