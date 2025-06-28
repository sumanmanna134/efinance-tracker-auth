/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.config;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Configuration
@Component
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${app.name}")
    private String appName;


    @Value("${app.version}")
    private String appVersion;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public static String AUTHORIZATION = "Authorization";
    public static String BEARER = "Bearer ";


    @PostConstruct
    public void init() throws InterruptedException {
        logger.info("App configuration loading..");
        Thread.sleep(500);
        logger.info("Configuration Loaded Successfully");
    }



}
