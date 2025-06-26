package com.otakumap.domain.animation.entity;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.mapping.AnimationHashtag;
import com.otakumap.domain.place_review.entity.PlaceReview;
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
public class Animation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "animation")
    private List<EventReview> eventReviews = new ArrayList<>();

    @OneToMany(mappedBy = "animation")
    private List<PlaceReview> placeReviews = new ArrayList<>();

    @OneToMany(mappedBy = "animation", cascade = CascadeType.ALL)
    private List<AnimationHashtag> animationHashtags = new ArrayList<>();
}
