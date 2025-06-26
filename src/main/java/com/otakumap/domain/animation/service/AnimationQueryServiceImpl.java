package com.otakumap.domain.animation.service;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.animation.repository.AnimationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimationQueryServiceImpl implements AnimationQueryService {
    private final AnimationRepository animationRepository;

    @Override
    public boolean existsById(Long animationId) {
        return animationRepository.existsById(animationId);
    }

    @Override
    @Transactional
    public List<Animation> searchAnimation(String keyword) {
        return animationRepository.searchAnimationByKeyword(keyword);
    }
}
