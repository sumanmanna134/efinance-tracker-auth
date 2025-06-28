/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.controller;

import com.efinance.efinance_tracker_auth.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ConfigController {

    @Autowired
    private NotificationService service;

    @GetMapping()
    public ResponseEntity<?> getMethod() {
        throw new UsernameNotFoundException("Username not found");
    }

}
