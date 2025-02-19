package com.otakumap.domain.event_short_review_reaction.entity;

import com.otakumap.domain.event_short_review.entity.EventShortReview;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "event_short_review_reaction", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "event_short_review_id"})})
public class EventShortReviewReaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_short_review_id", nullable = false)
    private EventShortReview eventShortReview;

    @Column(nullable = false)
    private boolean isLiked;

    @Column(nullable = false)
    private boolean isDisliked;

    public void updateLiked(boolean isLiked) { this.isLiked = isLiked; }

    public void updateDisliked(boolean isDisliked) { this.isDisliked = isDisliked; }
}