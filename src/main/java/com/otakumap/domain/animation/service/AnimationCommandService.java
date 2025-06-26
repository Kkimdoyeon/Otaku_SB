package com.otakumap.domain.animation.service;

import com.otakumap.domain.animation.entity.Animation;

public interface AnimationCommandService {
    Animation createAnimation(String name);
}
