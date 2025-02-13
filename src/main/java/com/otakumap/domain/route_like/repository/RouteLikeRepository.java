package com.otakumap.domain.route_like.repository;

import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route_like.entity.RouteLike;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteLikeRepository extends JpaRepository<RouteLike, Long> {
    boolean existsByUserAndRoute(User user, Route route);
    Optional<RouteLike> findByUserAndRoute(User user, Route route);
    int countByRoute(Route route);
}
