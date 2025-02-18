package com.otakumap.domain.event_reaction.repository;

import com.otakumap.domain.event_reaction.entity.EventReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventReactionRepository extends JpaRepository<EventReaction, Long> {
    Optional<EventReaction> findByUserIdAndEventShortReviewId(Long userId, Long eventShortReviewId);
}
