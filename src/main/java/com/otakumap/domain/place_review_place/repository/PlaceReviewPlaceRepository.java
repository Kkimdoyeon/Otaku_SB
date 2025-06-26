package com.otakumap.domain.place_review_place.repository;

import com.otakumap.domain.mapping.PlaceReviewPlace;
import com.otakumap.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceReviewPlaceRepository extends JpaRepository<PlaceReviewPlace, Long> {
    List<PlaceReviewPlace> findByPlace(Place place);
}
