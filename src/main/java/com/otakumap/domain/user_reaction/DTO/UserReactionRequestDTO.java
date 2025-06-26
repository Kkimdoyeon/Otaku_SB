package com.otakumap.domain.user_reaction.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

public class UserReactionRequestDTO {
    @Getter
    public static class ReactionRequestDTO {
        @Min(0)
        @Max(1)
        private int reactionType;
    }
}
