package com.otakumap.domain.reviews.service;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.animation.repository.AnimationRepository;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_animation.repository.EventAnimationRepository;
import com.otakumap.domain.event_location.entity.EventLocation;
import com.otakumap.domain.event_location.repository.EventLocationRepository;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.event_review_place.repository.EventReviewPlaceRepository;
import com.otakumap.domain.image.service.ImageCommandService;
import com.otakumap.domain.mapping.EventReviewPlace;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.PlaceReviewPlace;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.place_animation.repository.PlaceAnimationRepository;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.place_review_place.repository.PlaceReviewPlaceRepository;
import com.otakumap.domain.reviews.converter.ReviewConverter;
import com.otakumap.domain.reviews.dto.ReviewRequestDTO;
import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route.repository.RouteRepository;
import com.otakumap.domain.route_item.converter.RouteItemConverter;
import com.otakumap.domain.route_item.entity.RouteItem;
import com.otakumap.domain.route_item.repository.RouteItemRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.AnimationHandler;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final PlaceReviewRepository placeReviewRepository;
    private final EventReviewRepository eventReviewRepository;
    private final AnimationRepository animationRepository;
    private final PlaceRepository placeRepository;
    private final RouteRepository routeRepository;
    private final RouteItemRepository routeItemRepository;
    private final ImageCommandService imageCommandService;
    private final EventLocationRepository eventLocationRepository;
    private final PlaceAnimationRepository placeAnimationRepository;
    private final EventAnimationRepository eventAnimationRepository;
    private final PlaceReviewPlaceRepository placeReviewPlaceRepository;
    private final EventReviewPlaceRepository eventReviewPlaceRepository;

    @Override
    @Transactional
    public ReviewResponseDTO.CreatedReviewDTO createReview(ReviewRequestDTO.CreateDTO request, User user, MultipartFile[] images) {
        Animation animation = animationRepository.findById(request.getAnimeId())
                .orElseThrow(() -> new AnimationHandler(ErrorStatus.ANIMATION_NOT_FOUND));

        Route route = saveRoute(request.getTitle());
        List<RouteItem> routeItems = createRouteItems(request.getRouteItems(), animation, route);
        routeItemRepository.saveAll(routeItems);

        return saveReview(request, user, images, route, animation);
    }

    // request의 장소 목록을 route item 객체로 변환
    private List<RouteItem> createRouteItems(List<ReviewRequestDTO.RouteDTO> routeDTOs, Animation animation, Route route) {
        return routeDTOs.stream()
                .map(routeDTO -> {
                    Place place = findOrSavePlace(routeDTO);
                    RouteItem routeItem = RouteItemConverter.toRouteItem(routeDTO.getOrder(), place);
                    routeItem.setRoute(route);
                    associateAnimationWithPlaceOrEvent(place, animation, getItemType(place));
                    return routeItem;
                })
                .collect(Collectors.toList());
    }

    private Place findOrSavePlace(ReviewRequestDTO.RouteDTO routeDTO) {
        return placeRepository.findByNameAndLatAndLng(routeDTO.getName(), routeDTO.getLat(), routeDTO.getLng())
                .orElseGet(() -> placeRepository.save(ReviewConverter.toPlace(routeDTO)));
    }

    // 장소인지 이벤트인지 확인
    private ReviewType getItemType(Place place) {
        return eventLocationRepository.existsByLatAndLng(place.getLat(), place.getLng()) ? ReviewType.EVENT : ReviewType.PLACE;
    }

    // 장소 또는 이벤트에 애니메이션을 연결
    private PlaceAnimation associateAnimationWithPlaceOrEvent(Place place, Animation animation, ReviewType itemType) {
        if (itemType == ReviewType.PLACE) {
            return placeAnimationRepository.findByPlaceIdAndAnimationId(place.getId(), animation.getId())
                    .orElseGet(() -> placeAnimationRepository.save(ReviewConverter.toPlaceAnimation(place, animation))); // placeAnimation 반환
        } else {
            // Place에 해당하는 Event 찾기
            List<EventLocation> eventLocations = eventLocationRepository.findByLatAndLng(place.getLat(), place.getLng());
            EventLocation eventLocation = eventLocations.stream().findFirst()
                    .orElseThrow(() -> new ReviewHandler(ErrorStatus.EVENT_NOT_FOUND));
            Event event = eventLocation.getEvent();

            // Event 객체를 이용해 EventAnimation 생성 및 저장
            eventAnimationRepository.save(ReviewConverter.toEventAnimation(event, animation));

            return null; // Event는 따로 ID 반환할 필요 없음
        }
    }

    private Route saveRoute(String title) {
        return routeRepository.save(Route.builder()
                .name(title)
                .routeItems(new ArrayList<>())
                .build());
    }

    // 리뷰 저장 및 반환
    private ReviewResponseDTO.CreatedReviewDTO saveReview(ReviewRequestDTO.CreateDTO request, User user, MultipartFile[] images, Route route, Animation animation) {
        List<RouteItem> routeItems = routeItemRepository.findByRouteId(route.getId());

        List<Place> places = routeItems.stream()
                .map(routeItem -> placeRepository.findById(routeItem.getPlace().getId())
                        .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_NOT_FOUND)))
                .collect(Collectors.toList());

        if (request.getReviewType() == ReviewType.PLACE) {
            // 먼저 PlaceReview를 저장
            PlaceReview placeReview = ReviewConverter.toPlaceReview(request, user, new ArrayList<>(), route);
            placeReview = placeReviewRepository.save(placeReview);

            PlaceAnimation placeAnimation = associateAnimationWithPlaceOrEvent(places.get(0), animation, ReviewType.PLACE);
            placeReview.setPlaceAnimation(placeAnimation);
            placeReview = placeReviewRepository.save(placeReview);

            List<PlaceReviewPlace> placeReviewPlaces = ReviewConverter.toPlaceReviewPlaceList(places, placeReview);
            placeReviewPlaceRepository.saveAll(placeReviewPlaces);

            // placeList 업데이트 후 다시 저장
            placeReview.setPlaceList(placeReviewPlaces);
            placeReview = placeReviewRepository.save(placeReview);

            imageCommandService.uploadReviewImages(List.of(images), placeReview.getId(), ReviewType.PLACE);
            return ReviewConverter.toCreatedReviewDTO(placeReview.getId(), placeReview.getTitle());

        } else if (request.getReviewType() == ReviewType.EVENT) {
            // 먼저 EventReview를 저장
            EventReview eventReview = ReviewConverter.toEventReview(request, user, new ArrayList<>(), route);
            eventReview = eventReviewRepository.save(eventReview);

            // 저장된 EventReview를 기반으로 eventReviewPlaces 생성
            List<EventReviewPlace> eventReviewPlaces = ReviewConverter.toEventReviewPlaceList(places, eventReview);
            eventReviewPlaceRepository.saveAll(eventReviewPlaces);

            // placeList 업데이트 후 다시 저장
            eventReview.setPlaceList(eventReviewPlaces);
            eventReview = eventReviewRepository.save(eventReview);

            imageCommandService.uploadReviewImages(List.of(images), eventReview.getId(), ReviewType.EVENT);
            return ReviewConverter.toCreatedReviewDTO(eventReview.getId(), eventReview.getTitle());

        } else {
            throw new ReviewHandler(ErrorStatus.INVALID_REVIEW_TYPE);
        }
    }

}