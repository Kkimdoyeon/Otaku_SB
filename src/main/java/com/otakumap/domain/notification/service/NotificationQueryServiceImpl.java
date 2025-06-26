package com.otakumap.domain.notification.service;

import com.otakumap.domain.notification.dto.NotificationResponseDTO;
import com.otakumap.domain.notification.entity.Notification;
import com.otakumap.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        return notificationRepository.findAllByUserIdAndIsReadFalse(userId, sort);
    }
}
