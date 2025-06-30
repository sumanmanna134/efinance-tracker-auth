/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.entity;

import com.efinance.efinance_tracker_auth.enums.AccountType;
import com.efinance.efinance_tracker_auth.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private String id; // Custom primary key

    @NotBlank(message = "Full name is required")
    @Field("full_name")
    private String fullName;

    @Email(message = "Email must be valid")
    @Indexed(unique = true)
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Indexed(unique = true)
    private String phone;

    private String address;

    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private Gender gender;

    @Field(name = "date_of_birth")
    private String dateOfBirth;

    @Field("profile_pic_url")
    private String profilePicUrl;

    @Field("last_login")
    private LocalDateTime lastLogin;


    private boolean deactivated = false;

    @Pattern(regexp = "FREE|PREMIUM", message = "Account type must be FREE or PREMIUM")
    @Field("account_type")
    private AccountType accountType = AccountType.FREE;

}
