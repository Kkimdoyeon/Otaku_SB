package com.otakumap.domain.reviews.repository;

import com.otakumap.domain.animation.entity.QAnimation;
import com.otakumap.domain.event.entity.QEvent;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.entity.QEventReview;
import com.otakumap.domain.mapping.QEventAnimation;
import com.otakumap.domain.mapping.QPlaceAnimation;
import com.otakumap.domain.mapping.QPlaceReviewPlace;
import com.otakumap.domain.place.entity.QPlace;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.entity.QPlaceReview;
import com.otakumap.domain.reviews.converter.ReviewConverter;
import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.SearchHandler;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponseDTO.SearchedReviewPreViewDTO> getReviewsByKeyword(String keyword, int page, int size, String sort) {
        QEventReview eventReview = QEventReview.eventReview;
        QPlaceReview placeReview = QPlaceReview.placeReview;
        QEventAnimation eventAnimation = QEventAnimation.eventAnimation;
        QPlaceAnimation placeAnimation = QPlaceAnimation.placeAnimation;
        QAnimation animation = QAnimation.animation;
        QPlaceReviewPlace placeReviewPlace = QPlaceReviewPlace.placeReviewPlace;

        // 이벤트 리뷰 검색 : EventReview 제목, 내용, 또는 연관된 애니메이션 이름
        BooleanBuilder eventCondition = createSearchCondition(eventReview.title, eventReview.content, eventAnimation.animation.name, keyword);

        // 장소 리뷰 검색 : PlaceReview 제목, 내용, 또는 연관된 애니메이션 이름
        BooleanBuilder placeCondition = createSearchCondition(placeReview.title, placeReview.content, placeAnimation.animation.name, keyword);

        List<EventReview> eventReviews = queryFactory.selectFrom(eventReview)
                .leftJoin(eventReview.event, QEvent.event)
                .leftJoin(QEvent.event.eventAnimationList, eventAnimation)
                .leftJoin(eventAnimation.animation, animation)
                .where(eventCondition)
                .fetch();

        List<PlaceReview> placeReviews = queryFactory.selectFrom(placeReview)
                .leftJoin(placeReviewPlace).on(placeReviewPlace.placeReview.eq(placeReview))
                .leftJoin(placeReviewPlace.place, QPlace.place)
                .leftJoin(QPlace.place.placeAnimationList, placeAnimation)
                .leftJoin(placeAnimation.animation, animation)
                .where(placeCondition)
                .fetch();

        List<ReviewResponseDTO.SearchedReviewPreViewDTO> searchedReviews = new ArrayList<>();

        for(EventReview review : eventReviews) {
            searchedReviews.add(ReviewConverter.toSearchedEventReviewPreviewDTO(review));
        }

        for(PlaceReview review : placeReviews) {
            searchedReviews.add(ReviewConverter.toSearchedPlaceReviewPreviewDTO(review));
        }

        if (searchedReviews.isEmpty()) {
            throw new SearchHandler(ErrorStatus.REVIEW_SEARCH_NOT_FOUND);
        }

        // 정렬 (최신순, 조회수)
        sortReviews(searchedReviews, sort);

        return paginateReviews(searchedReviews, page, size);
    }

    private BooleanBuilder createSearchCondition(StringPath title, StringPath content, StringPath animationName, String keyword) {
        BooleanBuilder condition = new BooleanBuilder();

        condition.or(title.containsIgnoreCase(keyword))
                .or(content.containsIgnoreCase(keyword))
                .or(animationName.containsIgnoreCase(keyword));

        return condition;
    }

    private void sortReviews(List<ReviewResponseDTO.SearchedReviewPreViewDTO> reviews, String sort) {
        if ("views".equalsIgnoreCase(sort)) {
            reviews.sort(Comparator.comparing(ReviewResponseDTO.SearchedReviewPreViewDTO::getView).reversed()
                    .thenComparing(ReviewResponseDTO.SearchedReviewPreViewDTO::getCreatedAt)); // 조회수가 같으면 최신순을 기준으로
        } else {
            reviews.sort(Comparator.comparing(ReviewResponseDTO.SearchedReviewPreViewDTO::getCreatedAt).reversed());
        }
    }

    private Page<ReviewResponseDTO.SearchedReviewPreViewDTO> paginateReviews(
            List<ReviewResponseDTO.SearchedReviewPreViewDTO> reviews, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, reviews.size());
        if (start > end) {
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(page, size), reviews.size());
        }

        return new PageImpl<>(reviews.subList(start, end), PageRequest.of(page, size), reviews.size());
    }

    @Override
    public ReviewResponseDTO.Top7ReviewPreViewListDTO getTop7Reviews() {
        QPlaceReview placeReview = QPlaceReview.placeReview;
        QEventReview eventReview = QEventReview.eventReview;

        List<PlaceReview> placeReviews = queryFactory.select(placeReview)
                .from(placeReview)
                .orderBy(placeReview.view.desc())
                .limit(7)
                .fetch();
        List<EventReview> eventReviews = queryFactory.select(eventReview)
                .from(eventReview)
                .orderBy(eventReview.view.desc())
                .limit(7)
                .fetch();

        List<ReviewResponseDTO.Top7ReviewPreViewDTO> top7Reviews = Stream.concat(
                placeReviews.stream()
                        .map(ReviewConverter::toTop7PlaceReviewPreViewDTO),
                eventReviews.stream()
                        .map(ReviewConverter::toTop7EventReviewPreViewDTO)
        )
                .sorted(Comparator.comparing(ReviewResponseDTO.Top7ReviewPreViewDTO::getView, Comparator.reverseOrder())
                        .thenComparing(ReviewResponseDTO.Top7ReviewPreViewDTO::getCreatedAt, Comparator.reverseOrder()))
                .limit(7)
                .collect(Collectors.toList());

        return ReviewResponseDTO.Top7ReviewPreViewListDTO.builder()
                .reviews(top7Reviews)
                .build();
    }
}
