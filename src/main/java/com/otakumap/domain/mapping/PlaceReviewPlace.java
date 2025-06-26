package com.otakumap.domain.mapping;

import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlaceReviewPlace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_review_id")
    private PlaceReview placeReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void setPlaceReview(PlaceReview placeReview) { this.placeReview = placeReview; }

    public void setPlace(Place place) { this.place = place; }
}
