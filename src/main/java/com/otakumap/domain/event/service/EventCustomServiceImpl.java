package com.otakumap.domain.event.service;

import com.otakumap.domain.event.dto.EventResponseDTO;
import com.otakumap.domain.event.repository.EventRepositoryCustom;
import com.otakumap.domain.image.dto.ImageResponseDTO;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventCustomServiceImpl implements EventCustomService {
    private final EventRepositoryCustom eventRepository;

    @Override
    public List<EventResponseDTO.EventWithLikeDTO> getPopularEvents(User user) {
        return eventRepository.getPopularEvents(user);
    }

    @Override
    public ImageResponseDTO.ImageDTO getEventBanner() {
        return eventRepository.getEventBanner();
    }

    @Override
    public Page<EventResponseDTO.EventDTO> searchEventByCategory(String genre, String status, String type, Integer page, Integer size) {

        if (genre == null || genre.isBlank()) {
            if ((status == null || status.isBlank()) && (type == null || type.isBlank())) {
                // status, type, genre 모두 null인 경우 에러 발생
                throw new EventHandler(ErrorStatus.EVENT_CONDITION_NOT_FOUND);
            }
            // genre는 null, status나 type이 존재할 경우
            return eventRepository.getEventByStatusAndType(status, type, page, size);
        }
        // genre가 존재할 경우 처리
        return eventRepository.getEventByGenre(genre, page, size);
    }
}
