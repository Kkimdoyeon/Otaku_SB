package com.otakumap.domain.event_location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EventLocationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventLocationDTO {
        Long id;
        String name;
        Double longitude;
        Double latitude;
    }
}
