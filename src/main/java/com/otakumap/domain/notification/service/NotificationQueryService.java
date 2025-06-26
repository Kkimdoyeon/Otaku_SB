package com.otakumap.domain.notification.service;

import com.otakumap.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationQueryService {
    List<Notification> getUnreadNotifications(Long userId);
}
