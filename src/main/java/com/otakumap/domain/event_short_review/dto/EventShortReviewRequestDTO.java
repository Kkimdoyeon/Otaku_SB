package com.otakumap.domain.event_short_review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class EventShortReviewRequestDTO {
    @Getter
    public static class NewEventShortReviewDTO {
        Float rating;
        String content;
    }

    @Getter
    public static class UpdateEventShortReviewDTO {
        @NotNull(message = "평점 입력은 필수입니다.")
        Float rating;
        @NotBlank(message = "내용 입력은 필수입니다.")
        String content;
    }
}
