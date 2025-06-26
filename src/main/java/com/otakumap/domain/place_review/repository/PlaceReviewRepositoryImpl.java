package com.otakumap.domain.place_review.repository;

import com.otakumap.domain.animation.entity.QAnimation;
import com.otakumap.domain.mapping.QPlaceAnimation;
import com.otakumap.domain.place.entity.QPlace;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.entity.QPlaceReview;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.SortHandler;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.otakumap.domain.mapping.QPlaceReviewPlace.placeReviewPlace;

@Repository
@RequiredArgsConstructor
public class PlaceReviewRepositoryImpl implements PlaceReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PlaceReview> findAllReviewsByPlace(Long placeId, String sort) {

        QPlaceReview placeReview = QPlaceReview.placeReview;
        QAnimation animation = QAnimation.animation;
        QPlaceAnimation placeAnimation = QPlaceAnimation.placeAnimation;
        QPlace place = QPlace.place;

        // 동적 정렬 조건 생성
        OrderSpecifier<?>[] orderBy = getOrderSpecifier(placeReview, sort);

        return queryFactory.selectFrom(placeReview)
//                .join(placeReview.place, place) // PlaceReview에서 Place로 조인
                .join(placeReview.placeList, placeReviewPlace) // PlaceReview에서 PlaceReviewPlace로 조인
                .join(placeReviewPlace.place, place) // PlaceReviewPlace에서 Place로 조인
                .join(place.placeAnimationList, placeAnimation) // Place에서 PlaceAnimation으로 조인
                .join(placeAnimation.animation, animation) // PlaceAnimation에서 Animation으로 조인
                .where(place.id.eq(placeId))
                .orderBy(orderBy)
                .fetch();
    }

    private OrderSpecifier<?>[] getOrderSpecifier(QPlaceReview placeReview, String sort) {

        if ("views".equals(sort)) {
            return new OrderSpecifier[]{
                    placeReview.view.desc(),
                    placeReview.createdAt.desc() // 조회수가 동일하면 최신순으로 정렬
            };
        } else if ("latest".equals(sort)) {
            return new OrderSpecifier[]{
                    placeReview.createdAt.desc(),
                    placeReview.view.desc() // 최신순이 동일하면 조회수순으로 정렬
            };
        } else {
            throw new SortHandler(ErrorStatus.INVALID_SORT_TYPE);
        }
    }
}
