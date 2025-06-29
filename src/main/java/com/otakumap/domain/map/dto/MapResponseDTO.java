package com.otakumap.domain.map.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MapResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MapDetailDTO {
        private List<MapDetailEventDTO> events;
        private List<MapDetailPlaceDTO> places;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MapDetailEventDTO {
        private String type;
        private Long id;
        private String name;
        private LocalDate endDate;
        private Boolean isLiked;
        private String locationName;
        private Long animationId;
        private String animationName;
        private String thumbnail;
        private List<String> hashtags;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MapDetailPlaceDTO {
        private String type;
        private Long id;
        private String name;
        private String detail;
        private Boolean isLiked;
        private Long animationId;
        private String animationName;
        private List<String> hashtags;
    }
}
