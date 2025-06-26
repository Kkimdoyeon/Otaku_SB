package com.otakumap.domain.user_reaction.converter;

import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user_reaction.DTO.UserReactionResponseDTO;
import com.otakumap.domain.user_reaction.entity.UserReaction;

public class UserReactionConverter {
    public static UserReactionResponseDTO.ReactionResponseDTO toReactionResponseDTO(PlaceShortReview placeShortReview, UserReaction userReaction) {
        return UserReactionResponseDTO.ReactionResponseDTO.builder()
                .reviewId(placeShortReview.getId())
                .likes(placeShortReview.getLikes())
                .dislikes(placeShortReview.getDislikes())
                .isLiked(userReaction.isLiked())
                .isDisliked(userReaction.isDisliked())
                .build();
    }

    public static UserReaction toLike(User user, PlaceShortReview placeShortReview, boolean isLiked) {
        return UserReaction.builder()
                .user(user)
                .placeShortReview(placeShortReview)
                .isLiked(isLiked)
                .build();
    }

    public static UserReaction toDislike(User user, PlaceShortReview placeShortReview, boolean isDisliked) {
        return UserReaction.builder()
                .user(user)
                .placeShortReview(placeShortReview)
                .isDisliked(isDisliked)
                .build();
    }
}
