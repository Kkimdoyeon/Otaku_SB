package com.otakumap.domain.event_short_review_reaction.repository;

import com.otakumap.domain.event_short_review_reaction.entity.EventShortReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventShortReviewReactionRepository extends JpaRepository<EventShortReviewReaction, Long> {
    Optional<EventShortReviewReaction> findByUserIdAndEventShortReviewId(Long userId, Long eventShortReviewId);
}
