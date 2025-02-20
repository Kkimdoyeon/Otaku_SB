package com.otakumap.domain.event_review.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
    Page<EventReview> findAllByEvent(Event event, Pageable pageRequest);
    @Query("SELECT er FROM EventReview er JOIN er.routes r WHERE r.id = :routeId")
    Optional<EventReview> findByRouteId(@Param("routeId") Long routeId);
    @Query("SELECT r.eventReview.user FROM Route r WHERE r.id = :routeId")
    Optional<User> findUserByRouteId(@Param("routeId") Long routeId);
    @Query("SELECT er.user FROM EventReview er WHERE er.id = :reviewId")
    User findUserById(@Param("reviewId") Long reviewId);
    List<EventReview> findByIdAndIsWrittenTrue(Long reviewId);
}