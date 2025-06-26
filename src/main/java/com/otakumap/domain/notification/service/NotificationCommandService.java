package com.otakumap.domain.notification.service;

import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.user.entity.User;

public interface NotificationCommandService {
    void markAsRead(Long userId, Long notificationId);
    void notifyEventStarted(User user, Long eventId);
    void notifyRootSaved(User user, Long routeId, int rootCount);
    void notifyReviewPurchased(User purchaser, Long reviewId, ReviewType type);
}
