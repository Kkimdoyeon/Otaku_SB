package com.otakumap.domain.event_reaction.converter;

import com.otakumap.domain.event_reaction.dto.EventReactionResponseDTO;
import com.otakumap.domain.event_reaction.entity.EventReaction;
import com.otakumap.domain.event_short_review.entity.EventShortReview;
import com.otakumap.domain.user.entity.User;

public class EventReactionConverter {
    public static EventReactionResponseDTO.ReactionResponseDTO toReactionResponseDTO(EventShortReview eventShortReview, EventReaction eventReaction) {
        return EventReactionResponseDTO.ReactionResponseDTO.builder()
                .reviewId(eventShortReview.getId())
                .likes(eventShortReview.getLikes())
                .dislikes(eventShortReview.getDislikes())
                .isLiked(eventReaction.isLiked())
                .isDisliked(eventReaction.isDisliked())
                .build();
    }

    public static EventReaction toLike(User user, EventShortReview eventShortReview, boolean isLiked) {
        return EventReaction.builder()
                .user(user)
                .isLiked(isLiked)
                .build();
    }

    public static EventReaction toDislike(User user, EventShortReview eventShortReview, boolean isDisliked) {
        return EventReaction.builder()
                .user(user)
                .eventShortReview(eventShortReview)
                .isDisliked(isDisliked)
                .build();
    }
}
