/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.dto;

import com.efinance.efinance_tracker_auth.enums.AccountType;
import com.efinance.efinance_tracker_auth.enums.Gender;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfileDTO {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    private String address;

    private Gender gender;

    private String dateOfBirth;

    private String profilePicUrl;

    private LocalDateTime lastLogin;

    private boolean deactivated;

    private AccountType accountType;
}
