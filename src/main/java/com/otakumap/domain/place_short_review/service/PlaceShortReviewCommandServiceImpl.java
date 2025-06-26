package com.otakumap.domain.place_short_review.service;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.place_animation.repository.PlaceAnimationRepository;
import com.otakumap.domain.place_short_review.converter.PlaceShortReviewConverter;
import com.otakumap.domain.place_short_review.dto.PlaceShortReviewRequestDTO;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.place_short_review.repository.PlaceShortReviewRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceShortReviewCommandServiceImpl implements PlaceShortReviewCommandService {
    private final PlaceShortReviewRepository placeShortReviewRepository;
    private final PlaceRepository placeRepository;
    private final PlaceAnimationRepository placeAnimationRepository;

    @Override
    public PlaceShortReview createReview(User user, Long placeId, PlaceShortReviewRequestDTO.CreateDTO request) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_NOT_FOUND));

        PlaceAnimation placeAnimation = placeAnimationRepository.findByIdAndPlaceId(request.getPlaceAnimationId(), placeId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.INVALID_PLACE_ANIMATION));

        PlaceShortReview newReview = PlaceShortReviewConverter.toPlaceShortReview(request, user, place, placeAnimation);

        return placeShortReviewRepository.save(newReview);
    }

    @Override
    public void updatePlaceShortReview(Long placeShortReviewId, PlaceShortReviewRequestDTO.UpdatePlaceShortReviewDTO request) {
        PlaceShortReview placeShortReview = placeShortReviewRepository.findById(placeShortReviewId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_SHORT_REVIEW_NOT_FOUND));

        placeShortReview.setContent(request.getContent());
        placeShortReview.setRating(request.getRating());
    }

    @Override
    public void deletePlaceShortReview(Long placeShortReviewId) {
        PlaceShortReview placeShortReview = placeShortReviewRepository.findById(placeShortReviewId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_SHORT_REVIEW_NOT_FOUND));

        placeShortReviewRepository.delete(placeShortReview);
    }
}