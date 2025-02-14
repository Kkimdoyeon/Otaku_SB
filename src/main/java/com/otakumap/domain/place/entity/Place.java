package com.otakumap.domain.place.entity;

import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.mapping.EventReviewPlace;
import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.mapping.PlaceReviewPlace;
import com.otakumap.domain.place_short_review.entity.PlaceShortReview;
import com.otakumap.domain.route_item.entity.RouteItem;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "place")
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(name = "detail", nullable = false, length = 100)
    private String detail;

    @Column(name = "is_favorite", nullable = false)
    @ColumnDefault("false")
    private Boolean isFavorite;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_image_id", referencedColumnName = "id")
    private Image thumbnailImage;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceShortReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceAnimation> placeAnimationList = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceReviewPlace> placeReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<EventReviewPlace> eventReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteItem> routeItems = new ArrayList<>();
}
