package com.otakumap.domain.event_short_review_reaction.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

public class EventShortReviewReactionRequestDTO {
    @Getter
    public static class ReactionRequestDTO {
        @Min(0)
        @Max(1)
        private int reactionType;
    }
}
