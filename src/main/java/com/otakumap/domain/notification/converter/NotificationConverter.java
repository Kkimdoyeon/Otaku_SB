package com.otakumap.domain.notification.converter;

import com.otakumap.domain.notification.dto.NotificationResponseDTO;
import com.otakumap.domain.notification.entity.Notification;
import com.otakumap.domain.notification.entity.enums.NotificationType;
import com.otakumap.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationConverter {
    public static NotificationResponseDTO.NotificationDTO notificationDTO(Notification notification) {
        return NotificationResponseDTO.NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType().name())
                .message(notification.getMessage())
                .url(notification.getUrl())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .build();
    }

    public static NotificationResponseDTO.NotificationListDTO notificationListDTO(List<Notification> notifications) {
        return NotificationResponseDTO.NotificationListDTO.builder()
                .notifications(notifications.stream().map(NotificationConverter::notificationDTO).collect(Collectors.toList()))
                .build();
    }

    public static Notification toNotification(User user, NotificationType type, String message, String url) {
        return Notification.builder()
                .user(user)
                .type(type)
                .message(message)
                .url(url)
                .build();
    }
}
