/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.s3;

import com.efinance.efinance_tracker_auth.config.AppConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;

@Service
public class S3UploadService {

    @Autowired
    private AppConfig config;

    private S3Client s3Client;

    @PostConstruct
    public void init(){
        this.s3Client = S3Client.builder()
                .region(Region.of(config.getREGION()))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }


    public String uploadProfileImage(MultipartFile file, String userId) throws IOException {
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = config.getPROFILE_PIC_DIRECTORY() + "/" + userId + extension;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(config.getS3_BUCKET())
                .key(fileName)
                .acl(AppConfig.PUBLIC_ACL)
                .contentType(file.getContentType())
                .build();


        //https://efinance-bucket.s3.ap-south-1.amazonaws.com/profile_pic/33d7b206-07f6-485a-b7d4-e7466baeb8f2png
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return String.format("https://%s.s3.%s.amazonaws.com/%s", config.getS3_BUCKET(), config.getREGION(), fileName);


    }

    private String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf('.')+1);
    }



}
