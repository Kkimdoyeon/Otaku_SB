package com.otakumap.domain.event_reaction.service;

import com.otakumap.domain.event_reaction.converter.EventReactionConverter;
import com.otakumap.domain.event_reaction.entity.EventReaction;
import com.otakumap.domain.event_reaction.repository.EventReactionRepository;
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
public class EventReactionCommandServiceImpl implements EventReactionCommandService {
    private final EventReactionRepository eventReactionRepository;
    private final EventShortReviewRepository eventShortReviewRepository;

    @Override
    @Transactional
    public EventReaction reactToReview(User user, Long reviewId, int reactionType) {
        EventShortReview eventShortReview = eventShortReviewRepository.findById(reviewId).orElseThrow(() -> new ReviewHandler(ErrorStatus.PLACE_REVIEW_NOT_FOUND));

        EventReaction eventReaction = eventReactionRepository.findByUserIdAndEventShortReviewId(user.getId(), reviewId).orElse(null);

        if (eventReaction == null) {
            if (reactionType == 0) { // dislike
                eventReaction = EventReactionConverter.toDislike(user, eventShortReview, true);
                eventShortReview.updateDislikes(eventShortReview.getDislikes() + 1);
            } else { // like
                eventReaction = EventReactionConverter.toLike(user, eventShortReview, true);
                eventShortReview.updateLikes(eventShortReview.getLikes() + 1);
            }
        } else {
            if (reactionType == 0) { // dislike
                if (!eventReaction.isDisliked()) {
                    eventReaction.updateDisliked(true);
                    eventReaction.updateLiked(false);
                    eventShortReview.updateDislikes(eventShortReview.getDislikes() + 1);
                    if (eventReaction.isLiked()) {
                        eventShortReview.updateLikes(eventShortReview.getLikes() - 1);
                    }
                } else {
                    eventReaction.updateDisliked(false);
                    eventShortReview.updateDislikes(eventShortReview.getDislikes() - 1);
                }
            } else { // like
                if (!eventReaction.isLiked()) {
                    eventReaction.updateLiked(true);
                    eventReaction.updateDisliked(false);
                    eventShortReview.updateLikes(eventShortReview.getLikes() + 1);
                    if (eventReaction.isDisliked()) {
                        eventShortReview.updateDislikes(eventShortReview.getDislikes() - 1);
                    }
                } else {
                    eventReaction.updateLiked(false);
                    eventShortReview.updateLikes(eventShortReview.getLikes() - 1);
                }
            }
        }

        eventShortReviewRepository.save(eventShortReview);
        return eventReactionRepository.save(eventReaction);
    }
}