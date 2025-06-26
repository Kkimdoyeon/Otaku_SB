package com.otakumap.domain.mapping;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.place_like.entity.PlaceLike;
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
public class PlaceAnimation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animation_id")
    private Animation animation;

    @OneToMany(mappedBy = "placeAnimation", cascade = CascadeType.ALL)
    private List<PlaceLike> placeLikes = new ArrayList<>();
}
