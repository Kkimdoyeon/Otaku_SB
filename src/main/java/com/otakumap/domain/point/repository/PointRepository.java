package com.otakumap.domain.point.repository;

import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    Page<Point> findAllByUser(User user, PageRequest pageRequest);
    Point findTopByUserOrderByCreatedAtDesc(User user);
    List<Point> findByUserId(Long id);
}
