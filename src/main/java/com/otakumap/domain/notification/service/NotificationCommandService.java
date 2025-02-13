package com.otakumap.domain.notification.service;

import com.otakumap.domain.user.entity.User;

public interface NotificationCommandService {
    void markAsRead(Long userId, Long notificationId);
    void notifyEventStarted(User user, Long eventId);
    void notifyRootSaved(User user, Long routeId, int rootCount);
}
