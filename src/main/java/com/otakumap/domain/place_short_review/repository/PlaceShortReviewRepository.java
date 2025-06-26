package com.otakumap.domain.place_short_review.repository;

import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlaceShortReviewRepository extends JpaRepository<PlaceShortReview, Long> {
    Page<PlaceShortReview> findAllByPlace(Place place, Pageable pageRequest);
    List<PlaceShortReview> findAllByPlace(Place place);
}
