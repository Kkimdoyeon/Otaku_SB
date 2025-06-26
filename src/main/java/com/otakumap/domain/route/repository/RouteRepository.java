package com.otakumap.domain.route.repository;

import com.otakumap.domain.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
