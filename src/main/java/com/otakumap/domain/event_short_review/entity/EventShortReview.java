package com.otakumap.domain.event_short_review.entity;

import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    private int likes;

    @ColumnDefault("0")
    private int dislikes;

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
