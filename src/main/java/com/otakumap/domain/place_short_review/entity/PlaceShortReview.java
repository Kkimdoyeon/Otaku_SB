package com.otakumap.domain.place_short_review.entity;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user_reaction.entity.UserReaction;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlaceShortReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String content;

    @Column(nullable = false)
    private float rating;

    @Column(columnDefinition = "bigint default 0 not null")
    private Long likes;

    @Column(columnDefinition = "bigint default 0 not null")
    private Long dislikes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_animation_id")
    private PlaceAnimation placeAnimation;

    @OneToMany(mappedBy = "placeShortReview", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserReaction> reactions = new ArrayList<>();

    public void updateLikes(Long likes) { this.likes = likes; }

    public void updateDislikes(Long dislikes) { this.dislikes = dislikes; }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
