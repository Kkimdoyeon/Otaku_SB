package com.otakumap.domain.map.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.map.converter.MapConverter;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.EventAnimation;
import com.otakumap.domain.hash_tag.entity.HashTag;
import com.otakumap.domain.mapping.EventHashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MapRepositoryCustomImpl implements MapRepositoryCustom {

    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;

    @Override
    public MapResponseDTO.MapDetailDTO findAllMapDetails(Double latitude, Double longitude) {
        List<Event> eventList = eventRepository.findEventsByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailEventDTO> eventDTOs = eventList.stream().map(event -> {
            List<HashTag> eventHashTags = event.getEventHashTagList().stream()
                    .map(EventHashTag::getHashTag)
                    .collect(Collectors.toList());

            List<Animation> eventAnimations = event.getEventAnimationList().stream()
                    .map(EventAnimation::getAnimation)
                    .toList();

            return MapConverter.toMapDetailEventDTO(event, event.getEventLocation().getName(), eventAnimations.isEmpty() ? null : eventAnimations.get(0), eventHashTags);
        }).collect(Collectors.toList());

        List<Place> placeList = placeRepository.findPlacesByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailPlaceDTO> placeDTOs = placeList.stream().map(place -> {
            List<HashTag> placeHashTags = new ArrayList<>();
            place.getPlaceAnimationList().forEach(placeAnimation -> {
                placeAnimation.getPlaceAnimationHashTags().forEach(paht -> {
                    placeHashTags.add(paht.getHashTag());
                });
            });

            List<Animation> placeAnimations = place.getPlaceAnimationList().stream()
                    .map(PlaceAnimation::getAnimation)
                    .toList();

            return MapConverter.toMapDetailPlaceDTO(place, placeAnimations.isEmpty() ? null : placeAnimations.get(0), placeHashTags);
        }).collect(Collectors.toList());

        return MapConverter.toMapDetailDTO(eventDTOs, placeDTOs);
    }

}
