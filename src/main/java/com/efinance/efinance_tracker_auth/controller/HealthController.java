/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.controller;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.service.health.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private HealthService service;

    @GetMapping()
    public ResponseEntity<?> getHealth() {
        ApiResponse<Map<String, Object>> health = service.health();
        if(health.isSuccess()){
            return ResponseEntity.ok(health);
        }
        return ResponseEntity.internalServerError().body(health);
    }

}
