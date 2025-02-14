package com.otakumap.domain.place_short_review.service;

import com.otakumap.domain.place_short_review.dto.PlaceShortReviewRequestDTO;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.user.entity.User;

public interface PlaceShortReviewCommandService {
    PlaceShortReview createReview(User user, Long placeId, PlaceShortReviewRequestDTO.CreateDTO request);
    void updatePlaceShortReview(Long placeShortReviewId, PlaceShortReviewRequestDTO.UpdatePlaceShortReviewDTO request);
    void deletePlaceShortReview(Long placeShortReviewId);
}
