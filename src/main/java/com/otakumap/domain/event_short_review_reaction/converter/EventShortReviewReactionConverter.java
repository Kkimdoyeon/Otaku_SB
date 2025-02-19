package com.otakumap.domain.event_short_review_reaction.converter;

import com.otakumap.domain.event_short_review_reaction.dto.EventShortReviewReactionResponseDTO;
import com.otakumap.domain.event_short_review_reaction.entity.EventShortReviewReaction;
import com.otakumap.domain.event_short_review.entity.EventShortReview;
import com.otakumap.domain.user.entity.User;

public class EventShortReviewReactionConverter {
    public static EventShortReviewReactionResponseDTO.ReactionResponseDTO toReactionResponseDTO(EventShortReview eventShortReview, EventShortReviewReaction eventShortReviewReaction) {
        return EventShortReviewReactionResponseDTO.ReactionResponseDTO.builder()
                .reviewId(eventShortReview.getId())
                .likes(eventShortReview.getLikes())
                .dislikes(eventShortReview.getDislikes())
                .isLiked(eventShortReviewReaction.isLiked())
                .isDisliked(eventShortReviewReaction.isDisliked())
                .build();
    }

    public static EventShortReviewReaction toLike(User user, EventShortReview eventShortReview, boolean isLiked) {
        return EventShortReviewReaction.builder()
                .user(user)
                .eventShortReview(eventShortReview)
                .isLiked(isLiked)
                .build();
    }

    public static EventShortReviewReaction toDislike(User user, EventShortReview eventShortReview, boolean isDisliked) {
        return EventShortReviewReaction.builder()
                .user(user)
                .eventShortReview(eventShortReview)
                .isDisliked(isDisliked)
                .build();
    }
}
