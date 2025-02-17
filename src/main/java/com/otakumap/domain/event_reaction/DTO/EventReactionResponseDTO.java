package com.otakumap.domain.event_reaction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EventReactionResponseDTO {
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
