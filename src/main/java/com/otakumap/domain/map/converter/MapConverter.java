package com.otakumap.domain.map.converter;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.hash_tag.entity.HashTag;
import com.otakumap.domain.image.converter.ImageConverter;
import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.place.entity.Place;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapConverter {

    public static MapResponseDTO.MapDetailDTO toMapDetailDTO(
            List<MapResponseDTO.MapDetailEventDTO> eventList,
            List<MapResponseDTO.MapDetailPlaceDTO> placeList) {
        return MapResponseDTO.MapDetailDTO.builder()
                .events(eventList)
                .places(placeList)
                .build();
    }

    public static MapResponseDTO.MapDetailEventDTO toMapDetailEventDTO(
            Event event,
            Boolean isLiked,
            String locationName,
            Animation animation,
            List<HashTag> hashTags) {
        String image = "";
        if(event.getThumbnailImage() != null) {
            image = ImageConverter.toImageDTO(event.getThumbnailImage()).getFileUrl();
        }

        return MapResponseDTO.MapDetailEventDTO.builder()
                .type("event")
                .id(event.getId())
                .name(event.getName())
                .thumbnail(image)
                .endDate(event.getEndDate())
                .isLiked(isLiked)
                .locationName(locationName)
                .animationId(animation.getId())
                .animationName(animation.getName())
                .hashtags(hashTags.stream().map(HashTag::getName).collect(Collectors.toList()))
                .build();
    }

    public static MapResponseDTO.MapDetailPlaceDTO toMapDetailPlaceDTO(
            Place place,
            Boolean isLiked,
            Animation animation,
            List<HashTag> hashTags) {
        return MapResponseDTO.MapDetailPlaceDTO.builder()
                .type("place")
                .id(place.getId())
                .name(place.getName())
                .detail(place.getDetail())
                .isLiked(isLiked)
                .animationId(animation.getId())
                .animationName(animation.getName())
                .hashtags(hashTags.stream().map(HashTag::getName).collect(Collectors.toList()))
                .build();
    }
}
