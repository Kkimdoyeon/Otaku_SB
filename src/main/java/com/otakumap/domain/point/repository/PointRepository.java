package com.otakumap.domain.point.repository;

import com.otakumap.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByMerchantUid(String merchantUid);
}
