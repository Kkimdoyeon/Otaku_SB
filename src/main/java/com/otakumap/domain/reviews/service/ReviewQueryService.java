package com.otakumap.domain.reviews.service;

import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface ReviewQueryService {
    Page<ReviewResponseDTO.SearchedReviewPreViewDTO> searchReviewsByKeyword(String keyword, int page, int size, String sort);

    ReviewResponseDTO.ReviewDetailDTO getReviewDetail(Long reviewId, ReviewType type);

    ReviewResponseDTO.Top7ReviewPreViewListDTO getTop7Reviews();

    ReviewResponseDTO.IsPurchasedReviewDTO getIsPurchasedReview(User user, Long reviewId, ReviewType type);
}
