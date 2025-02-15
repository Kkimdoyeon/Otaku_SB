package com.otakumap.domain.place_like.repository;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place_like.entity.PlaceLike;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long> {
    boolean existsByUserAndPlaceAnimation(User user, PlaceAnimation placeAnimation);
}