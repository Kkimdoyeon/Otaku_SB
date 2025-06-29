package com.otakumap.domain.place_review.repository;

import com.otakumap.domain.place.entity.Place;
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
    @Query("SELECT pr FROM PlaceReview pr JOIN pr.routes r WHERE r.id = :routeId")
    Optional<PlaceReview> findByRouteId(@Param("routeId") Long routeId);
    @Query("SELECT r.placeReview.user FROM Route r WHERE r.id = :routeId")
    Optional<User> findUserByRouteId(@Param("routeId") Long routeId);
    @Query("SELECT pr.user FROM PlaceReview pr WHERE pr.id = :reviewId")
    User findUserById(@Param("reviewId") Long reviewId);
    @Query("SELECT pr FROM PlaceReview pr " +
            "JOIN PlaceReviewPlace prp ON prp.placeReview = pr " +
            "WHERE prp.place = :place AND pr.isWritten = true")
    List<PlaceReview> findByPlaceAndIsWrittenTrue(@Param("place") Place place);
}