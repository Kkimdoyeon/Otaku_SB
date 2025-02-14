package com.otakumap.domain.route.service;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.place.DTO.PlaceResponseDTO;
import com.otakumap.domain.place.converter.PlaceConverter;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.route.dto.RouteResponseDTO;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route.repository.RouteRepository;
import com.otakumap.domain.route_item.repository.RouteItemRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import com.otakumap.global.apiPayload.exception.handler.RouteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteQueryServiceImpl implements RouteQueryService {
    private final RouteRepository routeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final RouteItemRepository routeItemRepository;

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

        // routeId에 해당하는 PlaceReview 또는 EventReview 조회
        PlaceReview placeReview = placeReviewRepository.findByRouteId(routeId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_REVIEW_NOT_FOUND));

        Animation animation = null;
        if (placeReview.getPlaceAnimation() != null)
            animation = placeReview.getPlaceAnimation().getAnimation();

        // 관련된 애니메이션이 없으면 예외 발생
        if (animation == null)
            throw new RouteHandler(ErrorStatus.ROUTE_ANIMATION_NOT_FOUND);

        // PlaceDTO 변환
        List<PlaceResponseDTO.PlaceDTO> placeDTOs = PlaceConverter.toPlaceDTOList(places);

        return new RouteResponseDTO.RouteDetailDTO(
                route.getId(),
                route.getName(),
                animation.getId(),
                animation.getName(),
                placeDTOs
        );
    }
}