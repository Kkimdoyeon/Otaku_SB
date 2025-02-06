package com.otakumap.domain.event_location.entity;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EventLocation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String latitude;

    @Column(columnDefinition = "TEXT")
    private String longitude;

    @OneToOne(mappedBy = "eventLocation", fetch = FetchType.LAZY)
    private Event event;
}
