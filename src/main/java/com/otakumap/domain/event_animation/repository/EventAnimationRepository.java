package com.otakumap.domain.event_animation.repository;

import com.otakumap.domain.mapping.EventAnimation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAnimationRepository extends JpaRepository<EventAnimation, Long> {
}
