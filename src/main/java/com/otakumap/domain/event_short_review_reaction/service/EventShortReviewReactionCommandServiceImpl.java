package com.otakumap.domain.event_short_review_reaction.service;

import com.otakumap.domain.event_short_review_reaction.converter.EventShortReviewReactionConverter;
import com.otakumap.domain.event_short_review_reaction.entity.EventShortReviewReaction;
import com.otakumap.domain.event_short_review_reaction.repository.EventShortReviewReactionRepository;
import com.otakumap.domain.event_short_review.entity.EventShortReview;
import com.otakumap.domain.event_short_review.repository.EventShortReviewRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventShortReviewReactionCommandServiceImpl implements EventShortReviewReactionCommandService {
    private final EventShortReviewReactionRepository eventShortReviewReactionRepository;
    private final EventShortReviewRepository eventShortReviewRepository;

    @Override
    @Transactional
    public EventShortReviewReaction reactToReview(User user, Long reviewId, int reactionType) {
        EventShortReview eventShortReview = eventShortReviewRepository.findById(reviewId).orElseThrow(() -> new ReviewHandler(ErrorStatus.PLACE_REVIEW_NOT_FOUND));

        EventShortReviewReaction eventShortReviewReaction = eventShortReviewReactionRepository.findByUserIdAndEventShortReviewId(user.getId(), reviewId).orElse(null);

        if (eventShortReviewReaction == null) {
            if (reactionType == 0) { // dislike
                eventShortReviewReaction = EventShortReviewReactionConverter.toDislike(user, eventShortReview, true);
                eventShortReview.updateDislikes(eventShortReview.getDislikes() + 1);
            } else { // like
                eventShortReviewReaction = EventShortReviewReactionConverter.toLike(user, eventShortReview, true);
                eventShortReview.updateLikes(eventShortReview.getLikes() + 1);
            }
        } else {
            if (reactionType == 0) { // dislike
                if (!eventShortReviewReaction.isDisliked()) {
                    eventShortReviewReaction.updateDisliked(true);
                    eventShortReviewReaction.updateLiked(false);
                    eventShortReview.updateDislikes(eventShortReview.getDislikes() + 1);
                    if (eventShortReviewReaction.isLiked()) {
                        eventShortReview.updateLikes(eventShortReview.getLikes() - 1);
                    }
                } else {
                    eventShortReviewReaction.updateDisliked(false);
                    eventShortReview.updateDislikes(eventShortReview.getDislikes() - 1);
                }
            } else { // like
                if (!eventShortReviewReaction.isLiked()) {
                    eventShortReviewReaction.updateLiked(true);
                    eventShortReviewReaction.updateDisliked(false);
                    eventShortReview.updateLikes(eventShortReview.getLikes() + 1);
                    if (eventShortReviewReaction.isDisliked()) {
                        eventShortReview.updateDislikes(eventShortReview.getDislikes() - 1);
                    }
                } else {
                    eventShortReviewReaction.updateLiked(false);
                    eventShortReview.updateLikes(eventShortReview.getLikes() - 1);
                }
            }
        }

        eventShortReviewRepository.save(eventShortReview);
        return eventShortReviewReactionRepository.save(eventShortReviewReaction);
    }
}