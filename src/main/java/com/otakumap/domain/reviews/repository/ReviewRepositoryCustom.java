package com.otakumap.domain.reviews.repository;

import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface ReviewRepositoryCustom {
    Page<ReviewResponseDTO.SearchedReviewPreViewDTO> getReviewsByKeyword(String keyword, int page, int size, String sort);
    ReviewResponseDTO.Top7ReviewPreViewListDTO getTop7Reviews();
    ReviewResponseDTO.PurchaseReviewDTO purchaseReview(User user, Long reviewId, ReviewType type);
    Boolean getIsPurchasedReview(User user, Long reviewId, ReviewType type);
}
