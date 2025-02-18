package com.otakumap.domain.transaction.entity;


import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.transaction.enums.TransactionType;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    private Point point;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_review_id", referencedColumnName = "id")
    private EventReview eventReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_review_id", referencedColumnName = "id")
    private PlaceReview placeReview;


}
