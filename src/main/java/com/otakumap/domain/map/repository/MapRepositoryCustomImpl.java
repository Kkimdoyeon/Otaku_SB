package com.otakumap.domain.map.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.event_like.repository.EventLikeRepository;
import com.otakumap.domain.map.converter.MapConverter;
import com.otakumap.domain.mapping.AnimationHashtag;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.EventAnimation;
import com.otakumap.domain.hash_tag.entity.HashTag;
import com.otakumap.domain.place_like.repository.PlaceLikeRepository;
import com.otakumap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MapRepositoryCustomImpl implements MapRepositoryCustom {

    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;
    private final EventLikeRepository eventLikeRepository;
    private final PlaceLikeRepository placeLikeRepository;

    @Override
    public MapResponseDTO.MapDetailDTO findAllMapDetails(User user, Double latitude, Double longitude) {
        List<Event> eventList = eventRepository.findEventsByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailEventDTO> eventDTOs = eventList.stream().map(event -> {
            Boolean isLiked = Boolean.FALSE;

            List<Animation> eventAnimations = event.getEventAnimationList().stream()
                    .map(EventAnimation::getAnimation)
                    .toList();

            Animation eventAnimation = (!eventAnimations.isEmpty() ? eventAnimations.get(0) : null);
            List<HashTag> eventHashTags = new ArrayList<>();
            if(eventAnimation != null) {
                eventHashTags = eventAnimation.getAnimationHashtags().stream()
                        .map(AnimationHashtag::getHashTag)
                        .toList();
            }
            
            if(user!=null) {
                isLiked = eventLikeRepository.existsByUserAndEvent(user, event);
            }
            return MapConverter.toMapDetailEventDTO(event, isLiked, event.getEventLocation().getName(), eventAnimation, eventHashTags);
        }).collect(Collectors.toList());

        List<Place> placeList = placeRepository.findPlacesByLocationWithAnimations(latitude, longitude);
        List<MapResponseDTO.MapDetailPlaceDTO> placeDTOs = placeList.stream().map(place -> {
            List<HashTag> placeHashTags = new ArrayList<>();
            AtomicReference<Boolean> isLiked = new AtomicReference<>(Boolean.FALSE);

            place.getPlaceAnimationList().forEach(placeAnimation -> {
                placeAnimation.getAnimation().getAnimationHashtags().forEach(hashTag -> placeHashTags.add(hashTag.getHashTag()));

                if(user!=null) {
                    isLiked.set(placeLikeRepository.existsByUserAndPlaceAnimation(user, placeAnimation));
                }
            });

            List<Animation> placeAnimations = place.getPlaceAnimationList().stream()
                    .map(PlaceAnimation::getAnimation)
                    .toList();

            return MapConverter.toMapDetailPlaceDTO(place, isLiked.get(), placeAnimations.get(0), placeHashTags);
        }).collect(Collectors.toList());

        return MapConverter.toMapDetailDTO(eventDTOs, placeDTOs);
    }
}
