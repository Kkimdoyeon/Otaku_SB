package com.otakumap.domain.place_like.service;

import com.otakumap.domain.place_like.converter.PlaceLikeConverter;
import com.otakumap.domain.place_like.dto.PlaceLikeResponseDTO;
import com.otakumap.domain.place_like.entity.PlaceLike;
import com.otakumap.domain.place_like.entity.QPlaceLike;
import com.otakumap.domain.place_like.repository.PlaceLikeRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
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
public class PlaceLikeQueryServiceImpl implements PlaceLikeQueryService {
    private final PlaceLikeRepository placeLikeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PlaceLikeResponseDTO.PlaceLikePreViewListDTO getPlaceLikeList(User user, Boolean isFavorite, Long lastId, int limit) {
        QPlaceLike qPlaceLike = QPlaceLike.placeLike;
        BooleanBuilder predicate = new BooleanBuilder();

        //user가 좋아요(PlaceLike)를 누른 데이터만 조회
        predicate.and(qPlaceLike.user.eq(user));

        // isFavorite이 true일 때만 검색 조건에 추가
        if (isFavorite != null && isFavorite) {
            predicate.and(qPlaceLike .isFavorite.eq(isFavorite));
        }

        List<PlaceLike> result = jpaQueryFactory
                .selectFrom(qPlaceLike)
                .leftJoin(qPlaceLike.placeAnimation).fetchJoin()
                .leftJoin(qPlaceLike.user).fetchJoin()
                .where(predicate)
                .orderBy(qPlaceLike.createdAt.desc())
                .limit(limit + 1)
                .fetch();

        return createPlaceLikePreviewListDTO(result, limit);
    }

    private PlaceLikeResponseDTO.PlaceLikePreViewListDTO createPlaceLikePreviewListDTO(List<PlaceLike> placeLikes, int limit) {
        boolean hasNext = placeLikes.size() > limit;
        Long lastId = null;

        if (hasNext) {
            placeLikes = placeLikes.subList(0, placeLikes.size() - 1);
            lastId = placeLikes.get(placeLikes.size() - 1).getId();
        }

        List<PlaceLikeResponseDTO.PlaceLikePreViewDTO> list = placeLikes
                .stream()
                .map(placeLike -> PlaceLikeConverter.placeLikePreViewDTO(placeLike, placeLike.getPlaceAnimation().getPlace()))
                .collect(Collectors.toList());

        return PlaceLikeConverter.placeLikePreViewListDTO(list, hasNext, lastId);
    }


    @Override
    public boolean isPlaceLikeExist(Long id) {
        return placeLikeRepository.existsById(id);
    }

    @Override
    public PlaceLikeResponseDTO.PlaceLikeDetailDTO getPlaceLike(Long placeLikeId) {
        PlaceLike placeLike = placeLikeRepository.findById(placeLikeId).orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_LIKE_NOT_FOUND));
        return PlaceLikeConverter.placeLikeDetailDTO(placeLike, placeLike.getPlaceAnimation().getPlace());
    }
}