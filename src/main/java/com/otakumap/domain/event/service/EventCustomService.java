package com.otakumap.domain.event.service;

import com.otakumap.domain.event.dto.EventResponseDTO;
import com.otakumap.domain.image.dto.ImageResponseDTO;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventCustomService {
    List<EventResponseDTO.EventWithLikeDTO> getPopularEvents(User user);
    ImageResponseDTO.ImageDTO getEventBanner();
    Page<EventResponseDTO.EventDTO> searchEventByCategory(String genre, String status, String type, Integer page, Integer size);
}
