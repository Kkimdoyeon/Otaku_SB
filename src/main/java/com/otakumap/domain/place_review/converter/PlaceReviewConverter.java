package com.otakumap.domain.place_review.converter;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.image.converter.ImageConverter;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_review.dto.PlaceReviewResponseDTO;
import com.otakumap.domain.place_review.entity.PlaceReview;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceReviewConverter {
    // PlaceReview -> PlaceReviewDTO 변환
    public static PlaceReviewResponseDTO.PlaceReviewDTO toPlaceReviewDTO(PlaceReview placeReview) {

        return PlaceReviewResponseDTO.PlaceReviewDTO.builder()
                .reviewId(placeReview.getId())
                .placeIds(placeReview.getPlaceList().stream().map(prp -> prp.getPlace().getId()).collect(Collectors.toList())) // 해령: placeId -> placeIds로 변경
                .title(placeReview.getTitle())
                .content(placeReview.getContent())
                .view(placeReview.getView())
                .createdAt(placeReview.getCreatedAt())
                .reviewImage(ImageConverter.toImageDTO(!placeReview.getImages().isEmpty() ? placeReview.getImages().get(0) : null)) // 나중에 수정
                .type("place")
                .build();
    }

    // 해당 장소의 애니메이션별 리뷰 그룹 생성
    public static PlaceReviewResponseDTO.AnimationReviewGroupDTO toAnimationReviewGroupDTO(Animation animation, List<PlaceReview> reviews,
                                                                                           List<HashTagResponseDTO.HashTagDTO> hashTagDTOS) {

        List<PlaceReviewResponseDTO.PlaceReviewDTO> reviewDTOs = reviews.stream()
                .map(PlaceReviewConverter::toPlaceReviewDTO)
                .toList();

        return PlaceReviewResponseDTO.AnimationReviewGroupDTO.builder()
                .animationId(animation.getId())
                .animationName(animation.getName())
                .reviews(reviewDTOs)
                .hashTags(hashTagDTOS)
                .totalReviews(reviews.size())
                .build();
    }

    // 최상위 결과 DTO 생성
    public static PlaceReviewResponseDTO.PlaceAnimationReviewDTO toPlaceAnimationReviewDTO(Place place, long totalReviews,
                                                                                           List<PlaceReviewResponseDTO.AnimationReviewGroupDTO> animationGroups,
                                                                                           Float avgRating) {
        return PlaceReviewResponseDTO.PlaceAnimationReviewDTO.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .animationGroups(animationGroups)
                .totalReviews(totalReviews)
                .avgRating(avgRating)
                .build();
    }
}
