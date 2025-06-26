package com.otakumap.domain.reviews.service;

import com.otakumap.domain.reviews.dto.ReviewRequestDTO;
import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewCommandService {
    ReviewResponseDTO.CreatedReviewDTO createReview(ReviewRequestDTO.CreateDTO request, User user, MultipartFile[] images);
    ReviewResponseDTO.PurchaseReviewDTO purchaseReview(User user, Long reviewId, ReviewType type);
}
