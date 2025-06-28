/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.impl;

import com.efinance.efinance_tracker_auth.dto.UserInfoDto;
import com.efinance.efinance_tracker_auth.entity.UserInfo;
import com.efinance.efinance_tracker_auth.service.CustomUserDetails;
import com.efinance.efinance_tracker_auth.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserInfo user = userInfoService.getUserByUsername(username);
       return new CustomUserDetails(user);
    }

    public boolean checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userInfoService.isUserExist(userInfoDto.getUsername());
    }

    public boolean signUp(UserInfoDto userInfoDto){
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        if(checkIfUserAlreadyExist(userInfoDto)){
            return false;
        }

        String uid = UUID.randomUUID().toString();
        userInfoService.save(UserInfo.builder()
                .userId(uid)
                .username(userInfoDto.getUsername())
                .password(userInfoDto.getPassword())
                .roles(new HashSet<>())
                .build()
        );

       return true;
    }
}
