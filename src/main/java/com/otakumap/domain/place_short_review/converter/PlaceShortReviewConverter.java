package com.otakumap.domain.place_short_review.converter;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_short_review.dto.PlaceShortReviewRequestDTO;
import com.otakumap.domain.place_short_review.dto.PlaceShortReviewResponseDTO;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceShortReviewConverter {
    public static PlaceShortReviewResponseDTO.PlaceShortReviewDTO placeShortReviewDTO(PlaceShortReview placeShortReview) {
        PlaceShortReviewResponseDTO.PlaceShortReviewUserDTO user = PlaceShortReviewResponseDTO.PlaceShortReviewUserDTO.builder()
                .id(placeShortReview.getUser().getId())
                .nickname(placeShortReview.getUser().getNickname())
                //.profilePicture(placeShortReview.getUser().getProfilePicture()
                .build();

        return PlaceShortReviewResponseDTO.PlaceShortReviewDTO.builder()
                .id(placeShortReview.getId())
                .user(user)
                .content(placeShortReview.getContent())
                .rating(placeShortReview.getRating())
                .createdAt(placeShortReview.getCreatedAt())
                .likes(placeShortReview.getLikes())
                .dislikes(placeShortReview.getDislikes())
                .build();
    }

    public static PlaceShortReviewResponseDTO.PlaceShortReviewListDTO placeShortReviewListDTO(Page<PlaceShortReview> reviewList) {

        if(reviewList == null || reviewList.isEmpty()) return null;

        List<PlaceShortReviewResponseDTO.PlaceShortReviewDTO> placeShortReviewDTOList = reviewList.stream()
                .map(PlaceShortReviewConverter::placeShortReviewDTO).collect(Collectors.toList());

        PlaceShortReview review = reviewList.getContent().get(0);

        return PlaceShortReviewResponseDTO.PlaceShortReviewListDTO.builder()
                .placeId(review.getPlace().getId())
                .placeName(review.getPlace().getName())
                .currentPage(reviewList.getNumber() + 1)
                .totalPages(reviewList.getTotalPages())
                .shortReviews(placeShortReviewDTOList)
                .build();
    }

    public static PlaceShortReviewResponseDTO.CreateReviewDTO toCreateReviewDTO(PlaceShortReview placeShortReview) {
        Place place = placeShortReview.getPlace();
        PlaceAnimation placeAnimation = placeShortReview.getPlaceAnimation();
        return PlaceShortReviewResponseDTO.CreateReviewDTO.builder()
                .reviewId(placeShortReview.getId())
                .rating(placeShortReview.getRating())
                .content(placeShortReview.getContent())
                .createdAt(placeShortReview.getCreatedAt())
                .placeId(place.getId())
                .placeAnimationId(placeAnimation.getId())
                .build();
    }

    public static PlaceShortReview toPlaceShortReview(PlaceShortReviewRequestDTO.CreateDTO request, User user, Place place, PlaceAnimation placeAnimation) {
        return PlaceShortReview.builder()
                .user(user)
                .place(place)
                .placeAnimation(placeAnimation)
                .rating(request.getRating())
                .content(request.getContent())
                .dislikes(0L)
                .likes(0L)
                .build();
    }
}
