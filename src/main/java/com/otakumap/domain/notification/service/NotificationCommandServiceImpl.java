package com.otakumap.domain.notification.service;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.notification.converter.NotificationConverter;
import com.otakumap.domain.notification.entity.Notification;
import com.otakumap.domain.notification.entity.enums.NotificationType;
import com.otakumap.domain.notification.repository.NotificationRepository;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route.repository.RouteRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.NotificationHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService{
    private final NotificationRepository notificationRepository;
    private final EventRepository eventRepository;
    private final RouteRepository routeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final EventReviewRepository eventReviewRepository;

    @Override
    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationHandler(ErrorStatus.NOTIFICATION_NOT_FOUND));

        if (!notification.getUser().getId().equals(userId)) {
            throw new NotificationHandler(ErrorStatus.NOTIFICATION_ACCESS_DENIED);
        }

        if (notification.isRead()) {
            throw new NotificationHandler(ErrorStatus.NOTIFICATION_ALREADY_READ);
        }

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void notifyEventStarted(User user, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotificationHandler(ErrorStatus.EVENT_NOT_FOUND));

        String message = event.getName() + " 이벤트가 시작되었습니다! 지금 바로 확인하세요!";
        String url = "/api/events/" + eventId + "/details";
        notificationRepository.save(NotificationConverter.toNotification(user, NotificationType.EVENT_STARTED, message, url));
    }

    @Override
    @Transactional
    public void notifyRootSaved(User user, Long routeId, int rootCount) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new NotificationHandler(ErrorStatus.ROUTE_NOT_FOUND));

        String message = route.getName() + "의 루트 저장이 " + rootCount + "을(를) 돌파하였습니다!";
        String url = "/api/routes/" + routeId;
        notificationRepository.save(NotificationConverter.toNotification(user, NotificationType.ROOT_SAVED, message, url));
    }

    @Override
    @Transactional
    public void notifyReviewPurchased(User purchaser, Long reviewId, ReviewType type) {
        // 알림 전송받을 유저
        User user = null;
        if (type == ReviewType.EVENT) {
            user = eventReviewRepository.findUserById(reviewId);
        } else if (type == ReviewType.PLACE) {
            user = placeReviewRepository.findUserById(reviewId);
        } else {
            throw new NotificationHandler(ErrorStatus.INVALID_REVIEW_TYPE);
        }

        String message = purchaser.getNickname() + "님이 회원님을 후원했어요.";
        String url = "/api/reviews/" + reviewId + "?type=" + type;
        notificationRepository.save(NotificationConverter.toNotification(user, NotificationType.POST_SUPPORTED, message, url));
    }
}
