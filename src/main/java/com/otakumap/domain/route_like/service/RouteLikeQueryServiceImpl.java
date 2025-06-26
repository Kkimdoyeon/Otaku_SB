package com.otakumap.domain.route_like.service;

import com.otakumap.domain.route_like.converter.RouteLikeConverter;
import com.otakumap.domain.route_like.dto.RouteLikeResponseDTO;
import com.otakumap.domain.route_like.entity.QRouteLike;
import com.otakumap.domain.route_like.entity.RouteLike;
import com.otakumap.domain.route_like.repository.RouteLikeRepository;
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
public class RouteLikeQueryServiceImpl implements RouteLikeQueryService {
    private final RouteLikeRepository routeLikeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RouteLikeResponseDTO.RouteLikePreViewListDTO getRouteLikeList(User user, Boolean isFavorite, Long lastId, int limit) {
        QRouteLike qRouteLike = QRouteLike.routeLike;
        BooleanBuilder predicate = new BooleanBuilder();

        //user가 좋아요(RouteLike)를 누른 데이터만 조회
        predicate.and(qRouteLike.user.eq(user));

        // isFavorite이 true일 때만 검색 조건에 추가
        if (isFavorite != null && isFavorite) {
            predicate.and(qRouteLike .isFavorite.eq(isFavorite));
        }

        if (lastId != null && lastId > 0) {
            predicate.and(qRouteLike.id.lt(lastId));
        }

        List<RouteLike> result = jpaQueryFactory
                .selectFrom(qRouteLike)
                .leftJoin(qRouteLike.route).fetchJoin()  // Route 테이블 조인 (N:1)
                .leftJoin(qRouteLike.user).fetchJoin()   // User 테이블 조인 (N:1)
                .where(predicate)                        // 동적 필터링 적용
                .orderBy(qRouteLike.createdAt.desc())    // 최신순 정렬
                .limit(limit + 1)                        // limit보다 1개 더 조회 (hasNext 체크용)
                .fetch();

        return createRouteLikePreviewListDTO(result, limit);
    }

    private RouteLikeResponseDTO.RouteLikePreViewListDTO createRouteLikePreviewListDTO(List<RouteLike> routeLikes, int limit) {
        boolean hasNext = routeLikes.size() > limit;
        Long lastId = null;
        if (hasNext) {
            routeLikes = routeLikes.subList(0, routeLikes.size() - 1);
            lastId = routeLikes.get(routeLikes.size() - 1).getId();
        }
        List<RouteLikeResponseDTO.RouteLikePreViewDTO> list = routeLikes
                .stream()
                .map(routeLike -> RouteLikeConverter.routeLikePreViewDTO(routeLike, routeLike.getRoute()))
                .collect(Collectors.toList());

        return RouteLikeConverter.routeLikePreViewListDTO(list, hasNext, lastId);
    }

    @Override
    public boolean isRouteLikeExist(Long id) {
        return routeLikeRepository.existsById(id);
    }
}
