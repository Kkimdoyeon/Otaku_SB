package com.otakumap.domain.event_short_review_reaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EventShortReviewReactionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReactionResponseDTO {
        private Long reviewId;
        private Long likes;
        private Long dislikes;
        private Boolean isLiked;
        private Boolean isDisliked;
    }
}
