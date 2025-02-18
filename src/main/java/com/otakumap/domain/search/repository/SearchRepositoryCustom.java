package com.otakumap.domain.search.repository;

import com.otakumap.domain.event.dto.EventResponseDTO;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepositoryCustom {
    // 진행 전 or 진행 중인 이벤트에서 검색
    List<Event> searchEventsByKeyword(String keyword);

    // 모든 이벤트에서 검색
    Page<EventResponseDTO.EventDTO> searchAllEventsByKeyword(String keyword, Pageable pageRequest);

    List<Place> searchPlacesByKeyword(String keyword);
}
