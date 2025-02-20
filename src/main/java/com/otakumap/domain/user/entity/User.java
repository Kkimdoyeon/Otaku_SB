package com.otakumap.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.place_like.entity.PlaceLike;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.route_like.entity.RouteLike;
import com.otakumap.domain.user.entity.enums.Role;
import com.otakumap.domain.user.entity.enums.SocialType;
import com.otakumap.domain.user.entity.enums.UserStatus;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 30, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private SocialType socialType;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 15)
    private String nickname;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer donation;

    @ColumnDefault("1")
    @Column(nullable = false)
    private Boolean isCommunityActivityNotified;

    @ColumnDefault("1")
    @Column(nullable = false)
    private Boolean isEventBenefitsNotified;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'", nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'USER'", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    private Image profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PlaceLike> placeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RouteLike> routeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();

    public void encodePassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setNotification(Integer type, boolean isEnabled) {
        if (type == 1) { this.isCommunityActivityNotified = isEnabled; }
        else { this.isEventBenefitsNotified = isEnabled; }
    }

    public void setProfileImage(Image image) {
        this.profileImage = image;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void addEarnings(int price) {
        this.donation += price;
    }
}
