package com.otakumap.domain.event_like.service;

import com.otakumap.domain.event.entity.enums.EventType;
import com.otakumap.domain.event_like.converter.EventLikeConverter;
import com.otakumap.domain.event_like.dto.EventLikeResponseDTO;
import com.otakumap.domain.event_like.entity.EventLike;
import com.otakumap.domain.event_like.entity.QEventLike;
import com.otakumap.domain.event_like.repository.EventLikeRepository;
import com.otakumap.domain.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventLikeQueryServiceImpl implements EventLikeQueryService {
    private final EventLikeRepository eventLikeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public EventLikeResponseDTO.EventLikePreViewListDTO getEventLikeList(User user, Integer type, Boolean isFavorite, Long lastId, int limit) {
        EventType eventType = (type == null || type == 0) ? null : EventType.values()[type - 1];

        QEventLike qEventLike = QEventLike.eventLike;
        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(qEventLike.user.eq(user));

        if (eventType != null) {
            predicate.and(qEventLike.event.type.eq(eventType));
        }

        // isFavorite이 true일 때만 검색 조건에 추가
        if (isFavorite != null && isFavorite) {
            predicate.and(qEventLike .isFavorite.eq(isFavorite));
        }

        if (lastId != null && lastId > 0) {
            predicate.and(qEventLike.id.lt(lastId));
        }

        List<EventLike> result = jpaQueryFactory
                .selectFrom(qEventLike)
                .leftJoin(qEventLike.event).fetchJoin()
                .leftJoin(qEventLike.user).fetchJoin()
                .where(predicate)
                .orderBy(qEventLike.createdAt.desc())
                .limit(limit + 1)
                .fetch();

        return createEventLikePreviewListDTO(result, limit);
    }

    private EventLikeResponseDTO.EventLikePreViewListDTO createEventLikePreviewListDTO(List<EventLike> eventLikes, int limit) {
        boolean hasNext = eventLikes.size() > limit;
        Long lastId = null;
        if (hasNext) {
            eventLikes = eventLikes.subList(0, eventLikes.size() - 1);
            lastId = eventLikes.get(eventLikes.size() - 1).getId();
        }
        List<EventLikeResponseDTO.EventLikePreViewDTO> list = eventLikes
                .stream()
                .map(EventLikeConverter::eventLikePreViewDTO)
                .collect(Collectors.toList());

        return EventLikeConverter.eventLikePreViewListDTO(list, hasNext, lastId);
    }

    @Override
    public boolean isEventLikeExist(Long id) {
        return eventLikeRepository.existsById(id);
    }
}
