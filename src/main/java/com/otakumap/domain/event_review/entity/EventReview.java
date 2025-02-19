package com.otakumap.domain.event_review.entity;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.mapping.EventReviewPlace;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.transaction.entity.Transaction;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
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
public class EventReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "text not null")
    private String content;

    @Column(columnDefinition = "bigint default 0 not null")
    private Long view;

    @Column(columnDefinition = "bigint default 0 not null")
    private Long price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventReview")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "eventReview", cascade = CascadeType.ALL)
    private List<EventReviewPlace> placeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animation_id")
    private Animation animation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    @OneToMany(mappedBy = "eventReview", cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();

    public void setPlaceList(List<EventReviewPlace> placeList) { this.placeList = placeList; }

    public void setAnimation(Animation animation) { this.animation = animation; }
}
