package com.otakumap.domain.point.entity;

import com.otakumap.domain.payment.enums.PaymentStatus;
import com.otakumap.domain.transaction.entity.Transaction;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "point")
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 충전 받는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 충전된 포인트 금액
    @Column(nullable = false)
    private Long point;

    // 충전된 시간
    @Column(name = "charged_at", nullable = false, updatable = true)
    @CreationTimestamp
    private LocalDateTime chargedAt;

    // 충전한 사람
    @Column(name = "charged_by")
    private String chargedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();

    public Point(Long point, LocalDateTime chargedAt, PaymentStatus status, User user) {
        this.point = point;
        this.chargedAt = chargedAt;
        this.status = status;
        this.user = user;
    }

    public void addPoint(Long point) {
        this.point += point;
    }

    public Long subPoint(Long point) {
        this.point -= point;
        return this.point;
    }

    public boolean isAffordable(Long point) {
        return this.point >= point;
    }

    public void setPoint(Long totalPoint) {
        this.point = totalPoint;
    }

    public void setChargedAt(LocalDateTime now) {
        this.chargedAt = now;
    }
}