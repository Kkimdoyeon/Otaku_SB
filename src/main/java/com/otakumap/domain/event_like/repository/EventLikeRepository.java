package com.otakumap.domain.event_like.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_like.entity.EventLike;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventLikeRepository extends JpaRepository<EventLike, Long> {
    Boolean existsByUserAndEvent(User user, Event event);
    Optional<EventLike> findByEventIdAndUserId(Long eventId, Long userId);
}
