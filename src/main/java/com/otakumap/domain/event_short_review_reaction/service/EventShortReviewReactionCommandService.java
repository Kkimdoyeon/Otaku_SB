package com.otakumap.domain.event_short_review_reaction.service;

import com.otakumap.domain.event_short_review_reaction.entity.EventShortReviewReaction;
import com.otakumap.domain.user.entity.User;

public interface EventShortReviewReactionCommandService {
    EventShortReviewReaction reactToReview(User user, Long reviewId, int reactionType);
}
