package com.otakumap.domain.event_short_review.entity;

import com.otakumap.domain.event_short_review_reaction.entity.EventShortReviewReaction;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EventShortReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(length = 50, nullable = false)
    private String content;

    @Column(nullable = false)
    private Float rating;

    @ColumnDefault("0")
    private Long likes;

    @ColumnDefault("0")
    private Long dislikes;

    @OneToMany(mappedBy = "eventShortReview", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<EventShortReviewReaction> reactions = new ArrayList<>();

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void updateLikes(Long likes) { this.likes = likes; }

    public void updateDislikes(Long dislikes) { this.dislikes = dislikes; }
}
