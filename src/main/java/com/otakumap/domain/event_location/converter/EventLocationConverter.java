package com.otakumap.domain.event_location.converter;

import com.otakumap.domain.event_location.dto.EventLocationResponseDTO;
import com.otakumap.domain.event_location.entity.EventLocation;

public class EventLocationConverter {

    public static EventLocationResponseDTO.EventLocationDTO toEventLocationDTO(EventLocation eventLocation) {

        return EventLocationResponseDTO.EventLocationDTO.builder()
                .id(eventLocation.getId())
                .name(eventLocation.getName())
                .latitude(eventLocation.getLat())
                .longitude(eventLocation.getLng())
                .build();
    }
}
