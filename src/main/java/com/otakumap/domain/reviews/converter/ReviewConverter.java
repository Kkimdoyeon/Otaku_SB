package com.otakumap.domain.reviews.converter;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.image.converter.ImageConverter;
import com.otakumap.domain.mapping.EventAnimation;
import com.otakumap.domain.mapping.EventReviewPlace;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.PlaceReviewPlace;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.reviews.dto.ReviewRequestDTO;
import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.route.converter.RouteConverter;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static ReviewResponseDTO.Top7ReviewPreViewDTO toTop7EventReviewPreViewDTO(EventReview eventReview) {
        return ReviewResponseDTO.Top7ReviewPreViewDTO.builder()
                .id(eventReview.getId())
                .title(eventReview.getTitle())
                .reviewImage(eventReview.getImages() != null && !eventReview.getImages().isEmpty() ?
                        ImageConverter.toImageDTO(eventReview.getImages().get(0)) :
                        null) // 나중에 수정
                .view(eventReview.getView())
                .createdAt(eventReview.getCreatedAt())
                .type("event")
                .build();
    }

    public static ReviewResponseDTO.Top7ReviewPreViewDTO toTop7PlaceReviewPreViewDTO(PlaceReview eventReview) {
        return ReviewResponseDTO.Top7ReviewPreViewDTO.builder()
                .id(eventReview.getId())
                .title(eventReview.getTitle())
                .reviewImage(eventReview.getImages() != null && !eventReview.getImages().isEmpty() ?
                        ImageConverter.toImageDTO(eventReview.getImages().get(0)) :
                        null) // 나중에 수정
                .view(eventReview.getView())
                .createdAt(eventReview.getCreatedAt())
                .type("place")
                .build();
    }

    public static ReviewResponseDTO.SearchedReviewPreViewDTO toSearchedEventReviewPreviewDTO(EventReview eventReview) {
        return ReviewResponseDTO.SearchedReviewPreViewDTO.builder()
                .reviewId(eventReview.getId())
                .title(eventReview.getTitle())
                .content(eventReview.getContent())
                .reviewImage(ImageConverter.toImageDTO(!eventReview.getImages().isEmpty() ? eventReview.getImages().get(0) : null))
                .view(eventReview.getView())
                .createdAt(eventReview.getCreatedAt())
                .type("event")
                .build();
    }

    public static ReviewResponseDTO.SearchedReviewPreViewDTO toSearchedPlaceReviewPreviewDTO(PlaceReview placeReview) {

        return ReviewResponseDTO.SearchedReviewPreViewDTO.builder()
                .reviewId(placeReview.getId())
                .title(placeReview.getTitle())
                .content(placeReview.getContent())
                .reviewImage(ImageConverter.toImageDTO(!placeReview.getImages().isEmpty() ? placeReview.getImages().get(0) : null))
                .view(placeReview.getView())
                .createdAt(placeReview.getCreatedAt())
                .type("place")
                .build();
    }

    public static ReviewResponseDTO.ReviewDetailDTO toPlaceReviewDetailDTO(PlaceReview placeReview) {
        return ReviewResponseDTO.ReviewDetailDTO.builder()
                .reviewId(placeReview.getId())
                .animationName(placeReview.getPlaceAnimation().getAnimation().getName() != null ? placeReview.getPlaceAnimation().getAnimation().getName() : null)
                .title(placeReview.getTitle())
                .view(placeReview.getView())
                .content(placeReview.getContent())
                .reviewImages(placeReview.getImages().stream()
                        .filter(Objects::nonNull)
                        .map(ImageConverter::toImageDTO)
                        .toList())
                .userName(placeReview.getUser().getName())
                .profileImage(ImageConverter.toImageDTO(placeReview.getUser().getProfileImage()))
                .createdAt(placeReview.getCreatedAt())
                .route(RouteConverter.toRouteDTO(placeReview.getRoute()))
                .build();
    }

    public static ReviewResponseDTO.ReviewDetailDTO toEventReviewDetailDTO(EventReview eventReview) {
        return ReviewResponseDTO.ReviewDetailDTO.builder()
                .reviewId(eventReview.getId())
                .animationName(eventReview.getEventAnimation().getAnimation().getName() != null ? eventReview.getEventAnimation().getAnimation().getName() : null)
                .title(eventReview.getTitle())
                .view(eventReview.getView())
                .content(eventReview.getContent())
                .reviewImages(eventReview.getImages().stream()
                        .filter(Objects::nonNull)
                        .map(ImageConverter::toImageDTO)
                        .toList())
                .userName(eventReview.getUser().getName())
                .profileImage(ImageConverter.toImageDTO(eventReview.getUser().getProfileImage()))
                .createdAt(eventReview.getCreatedAt())
                .route(RouteConverter.toRouteDTO(eventReview.getRoute()))
                .build();
    }

    public static ReviewResponseDTO.CreatedReviewDTO toCreatedReviewDTO(Long reviewId, String title) {
        return ReviewResponseDTO.CreatedReviewDTO.builder()
                .reviewId(reviewId)
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static EventReview toEventReview(ReviewRequestDTO.CreateDTO request, User user, List<EventReviewPlace> eventReviewPlaces, Route route) {
        return EventReview.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .view(0L)
                .user(user)
                .placeList(eventReviewPlaces)
                .route(route)
                .rating(0F)
                .build();
    }

    public static PlaceReview toPlaceReview(ReviewRequestDTO.CreateDTO request, User user, List<PlaceReviewPlace> placeReviewPlaces, Route route) {
        return PlaceReview.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .view(0L)
                .user(user)
                .placeList(placeReviewPlaces)
                .route(route)
                .build();
    }

    public static List<PlaceReviewPlace> toPlaceReviewPlaceList(List<Place> places, PlaceReview placeReview) {
        return places.stream()
                .map(place -> PlaceReviewPlace.builder()
                        .placeReview(placeReview)
                        .place(place)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<EventReviewPlace> toEventReviewPlaceList(List<Place> places, EventReview eventReview) {
        return places.stream()
                .map(place -> EventReviewPlace.builder()
                        .eventReview(eventReview)
                        .place(place)
                        .build())
                .collect(Collectors.toList());
    }

    public static Place toPlace(ReviewRequestDTO.RouteDTO routeDTO) {
        return Place.builder()
                .name(routeDTO.getName())
                .lat(routeDTO.getLat())
                .lng(routeDTO.getLng())
                .detail(routeDTO.getDetail())
                .isFavorite(false)
                .build();
    }

    public static PlaceAnimation toPlaceAnimation(Place place, Animation animation) {
        return PlaceAnimation.builder()
                .place(place)
                .animation(animation)
                .build();
    }

    public static EventAnimation toEventAnimation(Event event, Animation animation) {
        return EventAnimation.builder()
                .event(event)
                .animation(animation)
                .build();
    }
}