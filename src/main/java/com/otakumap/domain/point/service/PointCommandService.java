package com.otakumap.domain.point.service;

import com.otakumap.domain.user.entity.User;

public interface PointCommandService {
    void chargePoints(User user, Long point, String merchantUid);
}
