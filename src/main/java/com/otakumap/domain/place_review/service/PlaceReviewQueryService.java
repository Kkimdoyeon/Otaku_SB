package com.otakumap.domain.place_review.service;

import com.otakumap.domain.place_review.dto.PlaceReviewResponseDTO;

public interface PlaceReviewQueryService {
    PlaceReviewResponseDTO.PlaceAnimationReviewDTO getReviewsByPlace(Long placeId, int page, int size, String sort);
}
