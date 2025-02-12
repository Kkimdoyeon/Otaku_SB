package com.otakumap.domain.place_review.service;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.hash_tag.converter.HashTagConverter;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.PlaceReviewPlace;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.place_review.converter.PlaceReviewConverter;
import com.otakumap.domain.place_review.dto.PlaceReviewResponseDTO;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.place_review_place.repository.PlaceReviewPlaceRepository;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.place_short_review.repository.PlaceShortReviewRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceReviewQueryServiceImpl implements PlaceReviewQueryService {

    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceShortReviewRepository placeShortReviewRepository;
    private final PlaceReviewPlaceRepository placeReviewPlaceRepository;

    @Override
    public PlaceReviewResponseDTO.PlaceAnimationReviewDTO getReviewsByPlace(Long placeId, int page, int size, String sort) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_NOT_FOUND));

        // 전체 한 줄 리뷰 평균 별점 구하기
        List<PlaceShortReview> placeShortReviewList = placeShortReviewRepository.findAllByPlace(place);
        double avgRating = placeShortReviewList.stream()
                .mapToDouble(PlaceShortReview::getRating)
                .average()
                .orElse(0.0);
        Float finalAvgRating = (float)(Math.round(avgRating * 10) / 10.0);

        // 전체 리뷰 리스트
        List<PlaceReviewPlace> placeReviewPlaces = placeReviewPlaceRepository.findByPlace(place);
        List<PlaceReview> allReviews = placeReviewPlaces.stream()
                .map(prp -> placeReviewRepository.findById(prp.getPlaceReview().getId()))
                .flatMap(Optional::stream)
                .toList();

        // 애니메이션별 해시태그
        List<PlaceAnimation> placeAnimations = allReviews.stream()
                .map(PlaceReview::getPlaceAnimation)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        Map<Animation, List<HashTagResponseDTO.HashTagDTO>> animationHashTagMap =
                placeAnimations.stream()
                        .collect(Collectors.groupingBy(
                                PlaceAnimation::getAnimation, // 그룹의 키 : PlaceAnimation이 참조하는 Animation

                                Collectors.flatMapping(
                                        pa -> pa.getPlaceAnimationHashTags().stream()
                                                .peek(pah -> log.info("PlaceAnimation 정보 : {}, HashTag : {}", pa.getAnimation().getName(), pah.getHashTag().getName()))
                                                .map(pah -> HashTagConverter.toHashTagDTO(pah.getHashTag())),
                                        Collectors.toList()
                                )
                        ));

        // 애니메이션별로 리뷰 그룹화
        Map<Animation, List<PlaceReview>> reviewsByAnimation = allReviews.stream()
                .collect(Collectors.groupingBy(review -> review.getPlaceAnimation().getAnimation()));

        // 애니메이션 그룹마다 그 안에 속한 리뷰들 페이징 적용
        List<PlaceReviewResponseDTO.AnimationReviewGroupDTO> animationGroups = paginateReviews(reviewsByAnimation, animationHashTagMap, page, size);

        // 총 리뷰 수 계산
        long totalReviews = reviewsByAnimation.values().stream()
                .mapToLong(List::size)
                .sum();

        return PlaceReviewConverter.toPlaceAnimationReviewDTO(place, totalReviews, animationGroups, finalAvgRating);
    }

    private List<PlaceReviewResponseDTO.AnimationReviewGroupDTO> paginateReviews(Map<Animation, List<PlaceReview>> reviewsByAnimation,
                                                                                 Map<Animation, List<HashTagResponseDTO.HashTagDTO>> animationHashTagMap,
                                                                                 int page, int size) {

        return  reviewsByAnimation.entrySet().stream()
                .map(entry -> {
                    Animation animation = entry.getKey();
                    List<PlaceReview> reviews = entry.getValue();
                    // 각 애니메이션에 해당하는 해시태그 목록을 가져오기
                    List<HashTagResponseDTO.HashTagDTO> hashTagsForAnimation = animationHashTagMap.getOrDefault(animation, Collections.emptyList());

                    int fromIndex = Math.min(page * size, reviews.size());
                    int toIndex = Math.min(fromIndex + size, reviews.size());

                    List<PlaceReview> pagedReviews = reviews.subList(fromIndex, toIndex);

                    return PlaceReviewConverter.toAnimationReviewGroupDTO(animation, pagedReviews, hashTagsForAnimation);
                })
                .filter(group -> !group.getReviews().isEmpty()) // 빈 그룹 제외
                .toList();
    }
}
