package com.otakumap.domain.event_reaction.service;

import com.otakumap.domain.event_reaction.entity.EventReaction;
import com.otakumap.domain.user.entity.User;

public interface EventReactionCommandService {
    EventReaction reactToReview(User user, Long reviewId, int reactionType);
}
