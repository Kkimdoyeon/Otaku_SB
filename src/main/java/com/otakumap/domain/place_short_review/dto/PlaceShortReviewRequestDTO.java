package com.otakumap.domain.place_short_review.DTO;

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
}
