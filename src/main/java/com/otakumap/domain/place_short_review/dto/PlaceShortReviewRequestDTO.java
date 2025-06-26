package com.otakumap.domain.place_short_review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class PlaceShortReviewRequestDTO {
    @Getter
    public static class CreateDTO {
        @NotNull
        Long placeAnimationId;
        @NotNull
        Float rating;
        @NotBlank
        String content;
    }

    @Getter
    public static class UpdatePlaceShortReviewDTO {
        @NotNull(message = "평점 입력은 필수입니다.")
        Float rating;
        @NotBlank(message = "내용 입력은 필수입니다.")
        String content;
    }
}
