package com.otakumap.domain.place.repository;

import com.otakumap.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
 List<Place> findByLatAndLng(Double lat, Double lng);
    Optional<Place> findByNameAndLatAndLng(String name, Double lat, Double lng);
    @Query("SELECT p FROM Place p " +
            "LEFT JOIN FETCH p.placeAnimationList pa " +
            "LEFT JOIN FETCH pa.animation " +
            "WHERE p.lat = :latitude AND p.lng = :longitude")
    List<Place> findPlacesByLocationWithAnimations(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}