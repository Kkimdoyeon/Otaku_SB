package com.otakumap.domain.route_like.entity;

import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "route_like")
public class RouteLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    // 즐겨찾기 여부
    @Column(name = "is_favorite", nullable = false)
    @ColumnDefault("false")
    private Boolean isFavorite;

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
