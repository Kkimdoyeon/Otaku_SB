package com.otakumap.domain.place_short_review.service;


import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.place_short_review.repository.PlaceShortReviewRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceShortReviewQueryServiceImpl implements PlaceShortReviewQueryService {
    private final PlaceShortReviewRepository placeShortReviewRepository;
    private final PlaceRepository placeRepository;


    @Override
    public Page<PlaceShortReview> getPlaceShortReviews(Long placeId, Integer page) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_NOT_FOUND));
        return placeShortReviewRepository.findAllByPlace(place, PageRequest.of(page, 6));
    }
}
