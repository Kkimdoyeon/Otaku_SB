package com.otakumap.domain.user_reaction.service;

import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user_reaction.entity.UserReaction;

public interface UserReactionCommandService {
    UserReaction reactToReview(User user, Long reviewId, int reactionType);
}
