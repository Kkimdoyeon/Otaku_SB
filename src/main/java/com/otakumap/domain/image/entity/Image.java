package com.otakumap.domain.image.entity;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String uuid;

    @Column(nullable = false, length = 100)
    private String fileName;

    @Column(nullable = false, length = 300)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_review_id")
    private PlaceReview placeReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_review_id")
    private EventReview eventReview;
}
