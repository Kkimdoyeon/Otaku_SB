package com.otakumap.domain.reviews.service;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.reviews.converter.ReviewConverter;
import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.reviews.repository.ReviewRepositoryCustom;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.EventHandler;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final EventReviewRepository eventReviewRepository;
    private final PlaceReviewRepository placeReviewRepository;

    @Override
    public Page<ReviewResponseDTO.SearchedReviewPreViewDTO> searchReviewsByKeyword(String keyword, int page, int size, String sort) {
        return reviewRepositoryCustom.getReviewsByKeyword(keyword, page, size, sort);
    }

    @Override
    public ReviewResponseDTO.Top7ReviewPreViewListDTO getTop7Reviews() {
        return reviewRepositoryCustom.getTop7Reviews();
    }

    @Override
    public ReviewResponseDTO.ReviewDetailDTO getReviewDetail(Long reviewId, ReviewType type) {
        if (type == ReviewType.EVENT) {
            List<EventReview> eventReviews = eventReviewRepository.findByIdAndIsWrittenTrue(reviewId);
            if (eventReviews.isEmpty()) {
                throw new EventHandler(ErrorStatus.EVENT_REVIEW_NOT_FOUND);
            }
            if (eventReviews.size() > 1) {
                throw new EventHandler(ErrorStatus.MULTIPLE_WRITTEN_EVENT_REVIEWS_FOUND);
            }
            return ReviewConverter.toEventReviewDetailDTO(eventReviews.get(0));
        } else if (type == ReviewType.PLACE) {
            List<PlaceReview> placeReviews = placeReviewRepository.findByIdAndIsWrittenTrue(reviewId);
            if (placeReviews.isEmpty()) {
                throw new PlaceHandler(ErrorStatus.PLACE_REVIEW_NOT_FOUND);
            }
            if (placeReviews.size() > 1) {
                throw new PlaceHandler(ErrorStatus.MULTIPLE_WRITTEN_PLACE_REVIEWS_FOUND);
            }
            return ReviewConverter.toPlaceReviewDetailDTO(placeReviews.get(0));
        } else {
            throw new ReviewHandler(ErrorStatus.INVALID_REVIEW_TYPE);
        }
    }
}