package com.otakumap.domain.animation.service;

import com.otakumap.domain.animation.entity.Animation;

import java.util.List;

public interface AnimationQueryService {
    List<Animation> searchAnimation(String keyword);
    boolean existsById(Long animationId);
}
