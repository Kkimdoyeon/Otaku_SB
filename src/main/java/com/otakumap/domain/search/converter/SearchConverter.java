package com.otakumap.domain.search.converter;

import com.otakumap.domain.event.dto.EventResponseDTO;
import com.otakumap.domain.place.DTO.PlaceResponseDTO;
import com.otakumap.domain.search.dto.SearchResponseDTO;

import java.util.List;

public class SearchConverter {

    public static SearchResponseDTO.SearchResultDTO toSearchResultDTO(List<EventResponseDTO.SearchedEventInfoDTO> eventDTOs,
                                                                      List<PlaceResponseDTO.SearchedPlaceInfoDTO> placeDTOs,
                                                                      Double lat, Double lng) {
        int placeAniCount = placeDTOs.stream()
                .mapToInt(placeDto -> placeDto.getAnimations().size())
                .sum();

        return SearchResponseDTO.SearchResultDTO.builder()
                .latitude(lat)
                .longitude(lng)
                .count(eventDTOs.size() + placeAniCount)
                .events(eventDTOs)
                .places(placeDTOs)
                .build();
    }
}
