package com.otakumap.domain.place_animation.repository;

import com.otakumap.domain.mapping.PlaceAnimation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceAnimationRepository extends JpaRepository<PlaceAnimation, Long> {
    Optional<PlaceAnimation> findByIdAndPlaceId(Long id, Long placeId);
    List<PlaceAnimation> findByPlaceId(Long placeId);
    Optional<PlaceAnimation> findByPlaceIdAndAnimationId(Long placeId, Long animationId);
}
