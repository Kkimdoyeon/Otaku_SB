package com.otakumap.domain.notification.entity;

import com.otakumap.domain.notification.entity.enums.NotificationType;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean isRead = false;

    private LocalDateTime readAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
        if (isRead) { this.readAt = LocalDateTime.now(); }
    }
}
