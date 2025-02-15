package com.otakumap.domain.map.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.event_like.repository.EventLikeRepository;
import com.otakumap.domain.map.converter.MapConverter;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.EventAnimation;
import com.otakumap.domain.hash_tag.entity.HashTag;
import com.otakumap.domain.mapping.EventHashTag;
import com.otakumap.domain.place_like.repository.PlaceLikeRepository;
import com.otakumap.domain.user.entity.User;
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
    private final EventLikeRepository eventLikeRepository;
    private final PlaceLikeRepository placeLikeRepository;

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

            return MapConverter.toMapDetailEventDTO(event, Boolean.FALSE, event.getEventLocation().getName(), eventAnimations.isEmpty() ? null : eventAnimations.get(0), eventHashTags);
        }).collect(Collectors.toList());

        List<Place> placeList = placeRepository.findPlacesByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailPlaceDTO> placeDTOs = placeList.stream().map(place -> {
            List<HashTag> placeHashTags = new ArrayList<>();
            place.getPlaceAnimationList().forEach(placeAnimation -> {
                placeAnimation.getPlaceAnimationHashTags().forEach(hashTag -> {
                    placeHashTags.add(hashTag.getHashTag());
                });
            });

            List<Animation> placeAnimations = place.getPlaceAnimationList().stream()
                    .map(PlaceAnimation::getAnimation)
                    .toList();

            return MapConverter.toMapDetailPlaceDTO(place, Boolean.FALSE, placeAnimations.isEmpty() ? null : placeAnimations.get(0), placeHashTags);
        }).collect(Collectors.toList());

        return MapConverter.toMapDetailDTO(eventDTOs, placeDTOs);
    }

    @Override
    public MapResponseDTO.MapDetailDTO findAllMapDetailsWithFavorite(User user, Double latitude, Double longitude) {
        List<Event> eventList = eventRepository.findEventsByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailEventDTO> eventDTOs = eventList.stream().map(event -> {
            List<HashTag> eventHashTags = event.getEventHashTagList().stream()
                    .map(EventHashTag::getHashTag)
                    .collect(Collectors.toList());

            List<Animation> eventAnimations = event.getEventAnimationList().stream()
                    .map(EventAnimation::getAnimation)
                    .toList();
            Boolean isLiked = eventLikeRepository.existsByUserAndEvent(user, event);
            return MapConverter.toMapDetailEventDTO(event, isLiked, event.getEventLocation().getName(), eventAnimations.isEmpty() ? null : eventAnimations.get(0), eventHashTags);
        }).collect(Collectors.toList());

        List<Place> placeList = placeRepository.findPlacesByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailPlaceDTO> placeDTOs = new ArrayList<>();

        placeList.forEach(place -> {
            List<HashTag> placeHashTags = new ArrayList<>();
            List<PlaceAnimation> placeAnimations = place.getPlaceAnimationList();

            if (!placeAnimations.isEmpty()) {
                placeAnimations.forEach(placeAnimation -> {
                    placeAnimation.getPlaceAnimationHashTags().forEach(hashTag -> {
                        placeHashTags.add(hashTag.getHashTag());
                    });
                    Boolean isLiked = placeLikeRepository.existsByUserAndPlaceAnimation(user, placeAnimation);

                    placeDTOs.add(MapConverter.toMapDetailPlaceDTO(place, isLiked, placeAnimation.getAnimation(), placeHashTags));
                });
            }
        });
        return MapConverter.toMapDetailDTO(eventDTOs, placeDTOs);
    }
}
