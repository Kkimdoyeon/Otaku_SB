package com.otakumap.domain.map.dto;

import com.otakumap.domain.image.dto.ImageResponseDTO;
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
        private String animationName;
        private ImageResponseDTO.ImageDTO thumbnail;
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
        private String animationName;
        private ImageResponseDTO.ImageDTO thumbnail;
        private List<String> hashtags;
    }
}
