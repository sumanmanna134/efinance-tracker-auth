/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.utils;

import com.efinance.efinance_tracker_auth.dto.ApiResponse;
import com.efinance.efinance_tracker_auth.exception.RefreshTokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorHelper {
    public static ResponseEntity<ApiResponse<Object>> getErrorResponse(Exception ex, HttpStatus httpStatus){
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), httpStatus);
        return ResponseEntity.status(httpStatus).body(response);
    }


}
