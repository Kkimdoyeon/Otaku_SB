package com.otakumap.domain.route.service;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.place.DTO.PlaceResponseDTO;
import com.otakumap.domain.place.converter.PlaceConverter;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.route.converter.RouteConverter;
import com.otakumap.domain.route.dto.RouteResponseDTO;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route.repository.RouteRepository;
import com.otakumap.domain.route_item.repository.RouteItemRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import com.otakumap.global.apiPayload.exception.handler.RouteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteQueryServiceImpl implements RouteQueryService {
    private final RouteRepository routeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final RouteItemRepository routeItemRepository;
    private final EventReviewRepository eventReviewRepository;

    @Override
    public boolean isRouteExist(Long routeId) {
        return routeRepository.existsById(routeId);
    }

    @Override
    public RouteResponseDTO.RouteDetailDTO getRouteDetail(User user, Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteHandler(ErrorStatus.ROUTE_NOT_FOUND));

        // routeId에 해당하는 Place 목록 조회
        List<Place> places = routeItemRepository.findPlacesByRouteId(routeId);

        // placeReview 또는 eventReview 조회
        Optional<PlaceReview> placeReviewOpt = placeReviewRepository.findByRouteId(routeId);
        Optional<EventReview> eventReviewOpt = eventReviewRepository.findByRouteId(routeId);

        // placeReview와 eventReview가 모두 없으면 예외 발생
        if (placeReviewOpt.isEmpty() && eventReviewOpt.isEmpty())
            throw new ReviewHandler(ErrorStatus.REVIEW_NOT_FOUND);

        Animation animation = null;
        if (placeReviewOpt.isPresent()) {
            animation = placeReviewOpt.get().getAnimation() != null ?
                    placeReviewOpt.get().getAnimation() : null;
        } else {
            animation = eventReviewOpt.get().getAnimation() != null ?
                    eventReviewOpt.get().getAnimation() : null;
        }

        // 관련된 애니메이션이 없으면 예외 발생
        if (animation == null)
            throw new RouteHandler(ErrorStatus.ROUTE_ANIMATION_NOT_FOUND);

        // PlaceDTO 변환
        List<PlaceResponseDTO.PlaceDTO> placeDTOs = PlaceConverter.toPlaceDTOList(places);

        return RouteConverter.toRouteDetailDTO(route, animation, placeDTOs);
    }
}