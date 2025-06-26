package com.otakumap.domain.notification.service;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.entity.enums.EventStatus;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.event_like.entity.EventLike;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventSchedulerService {

    private final EventRepository eventRepository;
    private final NotificationCommandService notificationCommandService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void checkAndNotifyEventStart() {
        List<Event> eventsStartingToday = eventRepository.findByStartDateAndStatus(LocalDate.now(), EventStatus.NOT_STARTED);

        for (Event event : eventsStartingToday) {
            event.startEvent(); // 이벤트 상태 변경 (진행 예정 → 진행 중)
            eventRepository.save(event); // 변경 사항 저장

            // 좋아요한 사용자들에게 알림 전송
            for (EventLike like : event.getEventLikeList()) {
                notificationCommandService.notifyEventStarted(like.getUser(), event.getId());
            }
        }
    }
}