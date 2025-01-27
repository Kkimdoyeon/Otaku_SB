package com.otakumap.domain.image.service;

import com.otakumap.domain.image.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageCommandService {
    Image uploadProfileImage(MultipartFile file, Long userId);
    List<Image> uploadReviewImages(List<MultipartFile> files, Long reviewId);
}

