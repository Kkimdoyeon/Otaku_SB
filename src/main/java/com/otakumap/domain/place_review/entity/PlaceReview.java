package com.otakumap.domain.place_review.entity;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.mapping.PlaceReviewPlace;
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
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PlaceReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    @Column(columnDefinition = "bigint default 0 not null")
    private Long view;

    @Column(columnDefinition = "bigint default 0 not null")
    private Long price;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isWritten = false; // 후기 작성 여부를 추적하는 boolean 필드

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeReview", orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "placeReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceReviewPlace> placeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animation_id")
    private Animation animation;

    @OneToMany(mappedBy = "placeReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes = new ArrayList<>();

    @OneToMany(mappedBy = "placeReview", cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();

    public void setPlaceList(List<PlaceReviewPlace> placeList) { this.placeList = placeList; }

    public void setAnimation(Animation animation) { this.animation = animation; }

    public void setIsWritten(boolean b) {
        this.isWritten = b;
    }

    public boolean getIsWritten() {
        return this.isWritten;
    }
}
