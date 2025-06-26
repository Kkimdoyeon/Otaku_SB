package com.otakumap.domain.animation.repository;

import com.otakumap.domain.animation.entity.Animation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimationRepository extends JpaRepository<Animation, Long> {
    @Query("SELECT a FROM Animation a WHERE REPLACE(LOWER(a.name), ' ', '') LIKE CONCAT('%', REPLACE(LOWER(:keyword), ' ', ''), '%')")
    List<Animation> searchAnimationByKeyword(@Param("keyword") String keyword);
}
