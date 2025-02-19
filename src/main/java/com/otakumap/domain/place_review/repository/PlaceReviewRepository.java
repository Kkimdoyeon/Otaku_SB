package com.otakumap.domain.place_review.repository;

import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long>, PlaceReviewRepositoryCustom {
    List<PlaceReview> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
    Optional<PlaceReview> findByRouteId(Long routeId);
    @Query("SELECT pr.user FROM PlaceReview pr WHERE pr.route.id = :routeId")
    Optional<User> findUserByRouteId(@Param("routeId") Long routeId);
    @Query("SELECT pr.user FROM PlaceReview pr WHERE pr.id = :reviewId")
    User findUserById(@Param("reviewId") Long reviewId);
}