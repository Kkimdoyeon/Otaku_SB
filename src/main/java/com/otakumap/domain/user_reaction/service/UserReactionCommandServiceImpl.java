package com.otakumap.domain.user_reaction.service;

import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.place_short_review.repository.PlaceShortReviewRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user_reaction.converter.UserReactionConverter;
import com.otakumap.domain.user_reaction.entity.UserReaction;
import com.otakumap.domain.user_reaction.repository.UserReactionRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReactionCommandServiceImpl implements UserReactionCommandService {
    private final UserReactionRepository userReactionRepository;
    private final PlaceShortReviewRepository placeShortReviewRepository;

    @Override
    @Transactional
    public UserReaction reactToReview(User user, Long reviewId, int reactionType) {
        PlaceShortReview placeShortReview = placeShortReviewRepository.findById(reviewId).orElseThrow(() -> new ReviewHandler(ErrorStatus.PLACE_REVIEW_NOT_FOUND));

        UserReaction userReaction = userReactionRepository.findByUserIdAndPlaceShortReviewId(user.getId(), reviewId).orElse(null);

        if (userReaction == null) {
            if (reactionType == 0) { // dislike
                userReaction = UserReactionConverter.toDislike(user, placeShortReview, true);
                placeShortReview.updateDislikes(placeShortReview.getDislikes() + 1);
            } else { // like
                userReaction = UserReactionConverter.toLike(user, placeShortReview, true);
                placeShortReview.updateLikes(placeShortReview.getLikes() + 1);
            }
        } else {
            if (reactionType == 0) { // dislike
                if (!userReaction.isDisliked()) {
                    userReaction.updateDisliked(true);
                    userReaction.updateLiked(false);
                    placeShortReview.updateDislikes(placeShortReview.getDislikes() + 1);
                    if (userReaction.isLiked()) {
                        placeShortReview.updateLikes(placeShortReview.getLikes() - 1);
                    }
                } else {
                    userReaction.updateDisliked(false);
                    placeShortReview.updateDislikes(placeShortReview.getDislikes() - 1);
                }
            } else { // like
                if (!userReaction.isLiked()) {
                    userReaction.updateLiked(true);
                    userReaction.updateDisliked(false);
                    placeShortReview.updateLikes(placeShortReview.getLikes() + 1);
                    if (userReaction.isDisliked()) {
                        placeShortReview.updateDislikes(placeShortReview.getDislikes() - 1);
                    }
                } else {
                    userReaction.updateLiked(false);
                    placeShortReview.updateLikes(placeShortReview.getLikes() - 1);
                }
            }
        }

        placeShortReviewRepository.save(placeShortReview);
        return userReactionRepository.save(userReaction);
    }
}