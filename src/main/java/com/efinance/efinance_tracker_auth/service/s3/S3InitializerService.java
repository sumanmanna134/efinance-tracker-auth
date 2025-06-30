/*
 * Copyright (c) 2025  Suman Manna
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.efinance.efinance_tracker_auth.service.s3;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Component
@RequiredArgsConstructor
public class S3InitializerService {
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;


    @Value("${aws.s3.profile-pic-folder}")
    private String profilePicFolder;

    private S3Client s3Client;

    private static final Logger logger = LoggerFactory.getLogger(S3InitializerService.class);

    @PostConstruct
    public void init(){
        try (DefaultCredentialsProvider defaultCredentialsProvider = DefaultCredentialsProvider.builder().build()) {
            s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(defaultCredentialsProvider)
                    .build();

            ensureBucketExists();
            ensureFolderExist();

        }

    }

    private void ensureBucketExists(){
        try{
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
            logger.info("✅ Bucket {} already exists.", bucketName);
        }catch (S3Exception ex){
            logger.info("❗ Bucket {} does not exist. Creating it..., message: {}", bucketName, ex.getMessage());
            createBucket(bucketName);
        }
    }

    private void createBucket(String bucket){
        CreateBucketRequest.Builder createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucket);

        if(!region.equalsIgnoreCase(String.valueOf(Region.US_EAST_1))){
            createBucketRequest.createBucketConfiguration(CreateBucketConfiguration.builder()
                    .locationConstraint(region).build());
        }

        s3Client.createBucket(createBucketRequest.build());
        logger.info("✅ Bucket created: {}", bucket);
    }

    private void ensureFolderExist(){
        String folderKey = profilePicFolder.endsWith("/") ? profilePicFolder : profilePicFolder + "/";
        try{
            s3Client.headObject(HeadObjectRequest.builder().bucket(bucketName).key(folderKey).build());
            logger.info("✅ S3 folder {} already exist.", folderKey);
        }catch (S3Exception ex){
            logger.info("❗ folder {} does not exist. Creating it... message: {}", folderKey, ex.getMessage());
            createFolder(folderKey);

        }
    }

    public void createFolder(String folderKey){
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(folderKey)
                .build(), RequestBody.empty());

        logger.info("✅ folder created: {}", folderKey);
    }
}
