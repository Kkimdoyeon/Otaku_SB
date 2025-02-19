package com.otakumap.domain.reviews.service;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.animation.repository.AnimationRepository;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.event_review_place.repository.EventReviewPlaceRepository;
import com.otakumap.domain.image.service.ImageCommandService;
import com.otakumap.domain.mapping.EventReviewPlace;
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
import com.otakumap.domain.reviews.repository.ReviewRepositoryCustom;
import com.otakumap.domain.route.converter.RouteConverter;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final PlaceReviewRepository placeReviewRepository;
    private final EventReviewRepository eventReviewRepository;
    private final AnimationRepository animationRepository;
    private final PlaceRepository placeRepository;
    private final RouteRepository routeRepository;
    private final RouteItemRepository routeItemRepository;
    private final ImageCommandService imageCommandService;
    private final PlaceAnimationRepository placeAnimationRepository;
    private final PlaceReviewPlaceRepository placeReviewPlaceRepository;
    private final EventReviewPlaceRepository eventReviewPlaceRepository;

    @Override
    @Transactional
    public ReviewResponseDTO.CreatedReviewDTO createReview(ReviewRequestDTO.CreateDTO request, User user, MultipartFile[] images) {
        Animation animation = animationRepository.findById(request.getAnimeId())
                .orElseThrow(() -> new AnimationHandler(ErrorStatus.ANIMATION_NOT_FOUND));

        Route route = routeRepository.save(RouteConverter.toRoute(request.getTitle()));
        List<RouteItem> routeItems = createRouteItems(request.getRouteItems(), animation, route);
        routeItemRepository.saveAll(routeItems);

        return saveReview(request, user, images, route, animation);
    }

    // request의 장소 목록을 route item 객체로 변환
    private List<RouteItem> createRouteItems(List<ReviewRequestDTO.RouteDTO> routeDTOs, Animation animation, Route route) {
        return routeDTOs.stream()
                .map(routeDTO -> {
                    // 위도, 경도에 해당하는 Place가 있으면 찾고 없으면 새로 생성해서 반환
                    Place place = findOrCreatePlace(routeDTO);
                    // place에 대한 placeAnimation 생성
                    createPlaceAnimation(place, animation);
                    // place, route에 대한 루트 아이템 생성
                    return RouteItemConverter.toRouteItem(routeDTO.getOrder(), route, place);
                })
                .collect(Collectors.toList());
    }

    private Place findOrCreatePlace(ReviewRequestDTO.RouteDTO routeDTO) {
        return placeRepository.findByNameAndLatAndLng(routeDTO.getName(), routeDTO.getLat(), routeDTO.getLng())
                .orElseGet(() -> placeRepository.save(ReviewConverter.toPlace(routeDTO)));
    }

    private void createPlaceAnimation(Place place, Animation animation) {
        placeAnimationRepository.findByPlaceIdAndAnimationId(place.getId(), animation.getId())
                .orElseGet(() -> placeAnimationRepository.save(ReviewConverter.toPlaceAnimation(place, animation))); // placeAnimation 반환
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
            PlaceReview placeReview = ReviewConverter.toPlaceReview(request, user, route);
            placeReview.setAnimation(animation);
            placeReview = placeReviewRepository.save(placeReview);

            // placeReviewPlace를 저장(placeReview에 해당하는 routeItem의 place 저장)
            List<PlaceReviewPlace> placeReviewPlaces = ReviewConverter.toPlaceReviewPlaceList(places, placeReview);
            placeReviewPlaceRepository.saveAll(ReviewConverter.toPlaceReviewPlaceList(places, placeReview));

            // placeList 업데이트
            placeReview.setPlaceList(placeReviewPlaces);

            if(images != null) {
                imageCommandService.uploadReviewImages(List.of(images), placeReview.getId(), ReviewType.PLACE);
            }

            return ReviewConverter.toCreatedReviewDTO(placeReview.getId(), placeReview.getTitle());
        } else if (request.getReviewType() == ReviewType.EVENT) {
            // 먼저 EventReview를 저장
            EventReview eventReview = ReviewConverter.toEventReview(request, user, route);
            eventReview.setAnimation(animation);
            eventReview = eventReviewRepository.save(eventReview);

            // eventReviewPlaces 저장(eventReview에 해당하는 routeItem의 place 저장)
            List<EventReviewPlace> eventReviewPlaces = ReviewConverter.toEventReviewPlaceList(places, eventReview);
            eventReviewPlaceRepository.saveAll(eventReviewPlaces);

            // placeList 업데이트
            eventReview.setPlaceList(eventReviewPlaces);

            if(images != null) {
                imageCommandService.uploadReviewImages(List.of(images), eventReview.getId(), ReviewType.EVENT);
            }
            return ReviewConverter.toCreatedReviewDTO(eventReview.getId(), eventReview.getTitle());
        } else {
            throw new ReviewHandler(ErrorStatus.INVALID_REVIEW_TYPE);
        }
    }

    @Override
    public ReviewResponseDTO.PurchaseReviewDTO purchaseReview(User user, Long reviewId, ReviewType type) {
        return reviewRepositoryCustom.purchaseReview(user, reviewId, type);
    }
}