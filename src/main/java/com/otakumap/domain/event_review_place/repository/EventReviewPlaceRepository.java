package com.otakumap.domain.event_review_place.repository;

import com.otakumap.domain.mapping.EventReviewPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReviewPlaceRepository extends JpaRepository<EventReviewPlace, Long> {
}
