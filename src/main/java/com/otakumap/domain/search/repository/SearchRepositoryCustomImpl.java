package com.otakumap.domain.search.repository;


import com.otakumap.domain.animation.entity.QAnimation;
import com.otakumap.domain.event.converter.EventConverter;
import com.otakumap.domain.event.dto.EventResponseDTO;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.entity.QEvent;
import com.otakumap.domain.event.entity.enums.EventStatus;
import com.otakumap.domain.mapping.QEventAnimation;
import com.otakumap.domain.mapping.QPlaceAnimation;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.entity.QPlace;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.SearchHandler;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryCustomImpl implements SearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    // 진행 전 or 진행 중인 이벤트 중에서 검색
    public List<Event> searchEventsByKeyword(String keyword) {

        QEvent qEvent = QEvent.event;
        QEventAnimation qEventAnimation = QEventAnimation.eventAnimation;
        QAnimation qAnimation = QAnimation.animation;

        BooleanBuilder condition = new BooleanBuilder();
        condition.or(qEvent.title.containsIgnoreCase(keyword))
                .or(qAnimation.name.containsIgnoreCase(keyword))
                .and(qEvent.status.ne(EventStatus.ENDED));

        return queryFactory.selectFrom(qEvent)
                .leftJoin(qEventAnimation).on(qEventAnimation.event.eq(qEvent))
                .leftJoin(qAnimation).on(qEventAnimation.animation.eq(qAnimation))
                .where(condition)
                .fetch();
    }

    @Override
    // 모든 이벤트에서 검색
    public Page<EventResponseDTO.EventDTO> searchAllEventsByKeyword(String keyword, Pageable pageRequest) {

        QEvent qEvent = QEvent.event;
        QEventAnimation qEventAnimation = QEventAnimation.eventAnimation;
        QAnimation qAnimation = QAnimation.animation;

        BooleanBuilder condition = new BooleanBuilder();
        condition.or(qEvent.title.containsIgnoreCase(keyword))
                .or(qAnimation.name.containsIgnoreCase(keyword));

        // 전체 건수 조회
        Long total = createBaseQuery(qEvent.id.count(), qEvent, qEventAnimation, qAnimation, condition)
                .fetchOne();
        if(total == null) {
            throw new SearchHandler(ErrorStatus.EVENT_SEARCH_NOT_FOUND);
        }

        List<Event> events = queryFactory.selectFrom(qEvent)
                .leftJoin(qEventAnimation).on(qEventAnimation.event.eq(qEvent))
                .leftJoin(qAnimation).on(qEventAnimation.animation.eq(qAnimation))
                .where(condition)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        List<EventResponseDTO.EventDTO> content = events.stream()
                .map(EventConverter::toEventDTO)
                .toList();

        return new PageImpl<>(content, pageRequest, total);
    }

    @Override
    public List<Place> searchPlacesByKeyword(String keyword) {

        QPlace qPlace = QPlace.place;
        QPlaceAnimation qPlaceAnimation = QPlaceAnimation.placeAnimation;
        QAnimation qAnimation = QAnimation.animation;

        BooleanBuilder condition = new BooleanBuilder();
        condition.or(qPlace.name.containsIgnoreCase(keyword))
                .or(qAnimation.name.containsIgnoreCase(keyword));

        return queryFactory.selectFrom(qPlace)
                .leftJoin(qPlaceAnimation).on(qPlaceAnimation.place.eq(qPlace))
                .leftJoin(qAnimation).on(qPlaceAnimation.animation.eq(qAnimation))
                .where(condition)
                .fetch();
    }

    private <T> JPAQuery<T> createBaseQuery(Expression<T> selectClause, QEvent qEvent,
                                            QEventAnimation qEventAnimation,
                                            QAnimation qAnimation, BooleanBuilder condition) {
        return queryFactory.select(selectClause)
                .from(qEvent)
                .leftJoin(qEventAnimation).on(qEventAnimation.event.eq(qEvent))
                .leftJoin(qAnimation).on(qEventAnimation.animation.eq(qAnimation))
                .where(condition);
    }
}
