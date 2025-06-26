package com.otakumap.domain.user_reaction.repository;

import com.otakumap.domain.user_reaction.entity.UserReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReactionRepository extends JpaRepository<UserReaction, Long> {
    Optional<UserReaction> findByUserIdAndPlaceShortReviewId(Long userId, Long placeShortReviewId);
}
