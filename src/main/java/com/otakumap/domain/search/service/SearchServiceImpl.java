package com.otakumap.domain.search.service;

import com.otakumap.domain.animation.converter.AnimationConverter;
import com.otakumap.domain.animation.dto.AnimationResponseDTO;
import com.otakumap.domain.event.converter.EventConverter;
import com.otakumap.domain.event.dto.EventResponseDTO;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.entity.enums.EventStatus;
import com.otakumap.domain.event_like.repository.EventLikeRepository;
import com.otakumap.domain.event_location.entity.EventLocation;
import com.otakumap.domain.event_location.repository.EventLocationRepository;
import com.otakumap.domain.hash_tag.converter.HashTagConverter;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.place.DTO.PlaceResponseDTO;
import com.otakumap.domain.place.converter.PlaceConverter;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.place_like.repository.PlaceLikeRepository;
import com.otakumap.domain.search.converter.SearchConverter;
import com.otakumap.domain.search.dto.SearchResponseDTO;
import com.otakumap.domain.search.repository.SearchRepositoryCustom;
import com.otakumap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepositoryCustom searchRepository;
    private final EventLocationRepository eventLocationRepository;
    private final EventLikeRepository eventLikeRepository;
    private final PlaceLikeRepository placeLikeRepository;
    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<SearchResponseDTO.SearchResultDTO> getSearchedResult (User user, String keyword) {

        List<Event> events = searchRepository.searchEventsByKeyword(keyword);
        List<Place> places = searchRepository.searchPlacesByKeyword(keyword);

        List<Place> safePlaces = places != null ? places : Collections.emptyList(); // 불변
        List<Event> safeEvents = events != null ? events : new ArrayList<>(); // 가변

        // 검색한 결과에 장소가 있을 때 -> 각 장소의 위/경도와 같은 곳에서 진행되고 있는 event를 찾음
        List<Event> newEvents = safePlaces.stream()
                .flatMap(place -> eventLocationRepository.findByLatAndLng(place.getLat(), place.getLng()).stream())
                .map(EventLocation::getEvent)
                .filter(event -> event != null && event.getStatus() != EventStatus.ENDED)
                .toList();

        // 기존에 검색된 이벤트와 합치기
        List<Event> combinedEvents =
                Stream.concat(safeEvents.stream(), newEvents.stream())
                        .distinct() // 중복 이벤트 제거
                        .toList();

        // 이벤트를 위치(위도, 경도) 기준으로 그룹화
        Map<String, List<Event>> groupedEvents = combinedEvents.stream()
                .filter(event -> event.getEventLocation() != null)
                .collect(Collectors.groupingBy(event ->
                        event.getEventLocation().getLat() + "," + event.getEventLocation().getLng()
                ));

        // 장소들을 위치 기준으로 그룹화 (같은 위치에 여러 명소가 있을 수 있으므로 그룹화)
        Map<String, List<Place>> groupedPlaces = safePlaces.stream()
                .collect(Collectors.groupingBy(place -> place.getLat() + "," + place.getLng()));

        // 만약 groupedEvents에는 존재하는 위치(key)가 groupedPlaces에 없다면,
        // 해당 위치의 장소들을 조회해서 groupedPlaces에 추가
        for (String key : groupedEvents.keySet()) {
            if (!groupedPlaces.containsKey(key)) {
                String[] parts = key.split(",");
                Double lat = Double.valueOf(parts[0]);
                Double lng = Double.valueOf(parts[1]);

                List<Place> additionalPlaces = placeRepository.findByLatAndLng(lat, lng);

                if (additionalPlaces != null && !additionalPlaces.isEmpty()) {
                    groupedPlaces.put(key, additionalPlaces);
                }
            }
        }

        // 모든 위치 키의 합집합
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(groupedEvents.keySet());
        allKeys.addAll(groupedPlaces.keySet());

        // 각 위치 키별로 이벤트와 장소 정보를 DTO로 변환하여 SearchResultDTO 생성
        return allKeys.stream().map(key -> {
            String[] parts = key.split(",");
            Double lat = Double.valueOf(parts[0]);
            Double lng = Double.valueOf(parts[1]);

            // 해당 위치의 이벤트들을 DTO로 변환
            List<EventResponseDTO.SearchedEventInfoDTO> eventDTOs = groupedEvents.getOrDefault(key, Collections.emptyList())
                    .stream().map(event -> {
                        boolean isLiked = false;
                        if(user != null) {
                            isLiked = eventLikeRepository.existsByUserAndEvent(user, event);
                        }

                        // 이벤트에 연결된 해시태그 조회
                        List<HashTagResponseDTO.HashTagDTO> eventHashTags = event.getEventAnimationList().stream()
                                .flatMap(ea -> ea.getAnimation().getAnimationHashtags().stream())
                                .map(ah -> HashTagConverter.toHashTagDTO(ah.getHashTag()))
                                .distinct()
                                .toList();

                        // eventAnimationList를 이용해 애니메이션 제목을 추출 (여러 개가 있을 경우 콤마로 연결)
                        String animationTitle = event.getEventAnimationList().stream()
                                .map(ea -> ea.getAnimation().getName())
                                .collect(Collectors.joining(", "));

                        return EventConverter.toSearchedEventInfoDTO(event, isLiked, animationTitle, eventHashTags);
                    })
                    .collect(Collectors.toList());

            // 장소 DTO 변환
            List<PlaceResponseDTO.SearchedPlaceInfoDTO> placeDTOs = groupedPlaces.getOrDefault(key, Collections.emptyList())
                    .stream().map(place -> {
                        // 각 장소의 모든 PlaceAnimation을 AnimationInfoDTO 리스트로 변환 (애니메이션별 좋아요 여부 계산)
                        List<AnimationResponseDTO.AnimationInfoDTO> animationDTOs = place.getPlaceAnimationList().stream()
                                .map(placeAnimation -> {
                                    boolean isLiked = false;
                                    // 각 장소의 애니메이션별 좋아요 여부
                                    if (user != null) {
                                        isLiked = placeLikeRepository.existsByUserAndPlaceAnimation(user, placeAnimation);
                                    }

                                    List<HashTagResponseDTO.HashTagDTO> placeHashTags = place.getPlaceAnimationList().stream()
                                            .flatMap(pa -> pa.getAnimation().getAnimationHashtags().stream())
                                            .map(ah -> HashTagConverter.toHashTagDTO(ah.getHashTag()))
                                            .distinct()
                                            .toList();

                                    return AnimationConverter.toAnimationInfoDTO(placeAnimation, isLiked, placeHashTags);
                                })
                                .collect(Collectors.toList());

                        return PlaceConverter.toSearchedPlaceInfoDTO(place, animationDTOs);
                    })
                    .toList();

            return SearchConverter.toSearchResultDTO(eventDTOs, placeDTOs, lat, lng);
        }).collect(Collectors.toList());
    }

}
