package com.otakumap.domain.place_review.repository;

import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.route.entity.Route;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long>, PlaceReviewRepositoryCustom {
    Page<PlaceReview> findAllByUserId(Long userId, PageRequest pageRequest);
    void deleteAllByUserId(Long userId);
    Optional<PlaceReview> findByRouteId(Long routeId);
    PlaceReview findAllByRoute(Route route);
}