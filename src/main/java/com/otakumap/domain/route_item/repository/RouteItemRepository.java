package com.otakumap.domain.route_item.repository;

import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.route_item.entity.RouteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteItemRepository extends JpaRepository<RouteItem, Long> {

    @Query("SELECT ri.place FROM RouteItem ri WHERE ri.route.id = :routeId AND ri.place.id = :placeId")
    Optional<Place> findPlaceByRouteIdAndPlaceId(@Param("routeId") Long routeId, @Param("placeId") Long placeId);

    @Query("SELECT ri.place FROM RouteItem ri WHERE ri.route.id = :routeId")
    List<Place> findPlacesByRouteId(@Param("routeId") Long routeId);

    List<RouteItem> findByRouteId(Long routeId);

    List<RouteItem> findAllByPlace(Place place);
}
