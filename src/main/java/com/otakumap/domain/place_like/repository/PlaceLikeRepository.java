package com.otakumap.domain.place_like.repository;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place_like.entity.PlaceLike;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long> {
    boolean existsByUserAndPlaceAnimation(User user, PlaceAnimation placeAnimation);
    // 특정 Place와 연결된 PlaceLike가 존재하는지 확인
    Optional<PlaceLike> findByPlaceIdAndUserId(Long placeId, Long userId);
}