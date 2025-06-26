package com.otakumap.domain.place_short_review.service;

import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import org.springframework.data.domain.Page;

public interface PlaceShortReviewQueryService {
    Page<PlaceShortReview> getPlaceShortReviews(Long placeId, Integer page);
}
