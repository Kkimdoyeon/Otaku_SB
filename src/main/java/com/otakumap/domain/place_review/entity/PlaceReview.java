package com.otakumap.domain.place_review.entity;

import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.PlaceReviewPlace;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.route.entity.Route;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeReview", orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "placeReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceReviewPlace> placeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_animation_id")
    private PlaceAnimation placeAnimation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    public void setPlaceList(List<PlaceReviewPlace> placeList) { this.placeList = placeList; }

    public void setPlaceAnimation(PlaceAnimation placeAnimation) { this.placeAnimation = placeAnimation; }
}
