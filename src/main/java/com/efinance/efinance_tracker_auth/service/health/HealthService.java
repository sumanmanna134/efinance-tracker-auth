/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.health;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class HealthService {

    public ApiResponse<Map<String, Object>> health() {
        Map<String,Object> info = new LinkedHashMap<>();
        info.put("status", "UP");
        info.put("timestamp", LocalDateTime.now().toString());

        // Runtime info
        Runtime runtime = Runtime.getRuntime();
        info.put("availableProcessors", runtime.availableProcessors());
        info.put("freeMemoryMB", runtime.freeMemory() / (1024 * 1024));
        info.put("totalMemoryMB", runtime.totalMemory() / (1024 * 1024));
        info.put("maxMemoryMB", runtime.maxMemory() / (1024 * 1024));

        // OS info
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        info.put("osName", os.getName());
        info.put("osArch", os.getArch());
        info.put("osVersion", os.getVersion());
        info.put("systemLoadAverage", os.getSystemLoadAverage());

        // JVM uptime
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        info.put("jvmUptimeMillis", runtimeMXBean.getUptime());
        info.put("jvmStartTime", Instant.ofEpochMilli(runtimeMXBean.getStartTime()).toString());

        try {
            InetAddress address = InetAddress.getLocalHost();
            info.put("hostName", address.getHostName());
            info.put("hostAddress", address.getHostAddress());
            return ApiResponse.success(info, "Server health");
        } catch (UnknownHostException e) {
            info.put("hostError", e.getMessage());
            return ApiResponse.error(info.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
