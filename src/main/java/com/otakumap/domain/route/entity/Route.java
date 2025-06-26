package com.otakumap.domain.route.entity;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.route_item.entity.RouteItem;
import com.otakumap.domain.route_like.entity.RouteLike;
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
public class Route extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<RouteLike> routeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteItem> routeItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_review_id")
    private PlaceReview placeReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_review_id")
    private EventReview eventReview;

    public void setName(String name) {
        this.name = name;
    }

    public void setRouteItems(List<RouteItem> routeItems) {
        if (!this.routeItems.isEmpty()) {
            this.routeItems.clear();
        }
        this.routeItems.addAll(routeItems);
    }

    public void setPlaceReview(PlaceReview placeReview) {
        this.placeReview = placeReview;
        this.eventReview = null; // 이벤트 리뷰와 동시에 연결되지 않도록
    }

    public void setEventReview(EventReview eventReview) {
        this.eventReview = eventReview;
        this.placeReview = null; // 장소 리뷰와 동시에 연결되지 않도록
    }
}
