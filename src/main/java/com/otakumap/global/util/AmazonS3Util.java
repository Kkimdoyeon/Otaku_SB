package com.otakumap.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.otakumap.global.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Util {
    private final AmazonS3 amazonS3;
    private final AmazonConfig amazonConfig;

    public String uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata);
        } catch (IOException e) {
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    public String generateProfileKeyName() {
        return amazonConfig.getProfilePath() + '/' + UUID.randomUUID().toString();
    }

    public String generateReviewKeyName() {
        return amazonConfig.getReviewPath() + '/' + UUID.randomUUID().toString();
    }

    public String generateEventKeyName() {
        return amazonConfig.getEventPath() + '/' + UUID.randomUUID().toString();
    }
}