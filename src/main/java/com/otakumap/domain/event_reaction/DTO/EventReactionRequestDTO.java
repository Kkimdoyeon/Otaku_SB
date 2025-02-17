package com.otakumap.domain.event_reaction.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

public class EventReactionRequestDTO {
    @Getter
    public static class ReactionRequestDTO {
        @Min(0)
        @Max(1)
        private int reactionType;
    }
}
