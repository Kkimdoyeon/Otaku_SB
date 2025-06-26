package com.otakumap.domain.place_review.repository;

import com.otakumap.domain.place_review.entity.PlaceReview;

import java.util.List;

public interface PlaceReviewRepositoryCustom {

    List<PlaceReview> findAllReviewsByPlace(Long placeId, String sort);
}
