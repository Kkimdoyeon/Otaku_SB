package com.otakumap.domain.mapping;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EventReviewPlace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_review_id")
    private EventReview eventReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void setEventReview(EventReview eventReview) { this.eventReview = eventReview; }
}
