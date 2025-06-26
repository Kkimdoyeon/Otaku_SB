package com.otakumap.domain.event.dto;

import com.otakumap.domain.event_location.dto.EventLocationResponseDTO;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.image.dto.ImageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

public class EventResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDTO {
        Long id;
        String title;
        LocalDate startDate;
        LocalDate endDate;
        ImageResponseDTO.ImageDTO thumbnail;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventWithLikeDTO {
        Long id;
        String title;
        Boolean isLiked;
        LocalDate startDate;
        LocalDate endDate;
        ImageResponseDTO.ImageDTO thumbnail;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDetailDTO {
        Long id;
        String title;
        String animationName;
        String name;
        String site;
        LocalDate startDate;
        LocalDate endDate;
        ImageResponseDTO.ImageDTO thumbnailImage;
        ImageResponseDTO.ImageDTO backgroundImage;
        ImageResponseDTO.ImageDTO goodsImage;
        EventLocationResponseDTO.EventLocationDTO eventLocation;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventSearchResultDTO {
        List<EventDTO> events;
        Integer pageNumber;
        Integer totalPages;
        Integer totalElements;
        Boolean isLast;
    }
      
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchedEventInfoDTO {
        Long eventId;
        String name;
        Boolean isLiked;
        private String animationTitle;
        List<HashTagResponseDTO.HashTagDTO> hashTags;
    }
}
