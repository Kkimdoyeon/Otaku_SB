package com.otakumap.domain.event.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Genre {
    ROMANCE("순정"),
    ACTION("액션"),
    FANTASY("판타지"),
    THRILLER("스릴러"),
    SPORTS("스포츠");

    private final String description;
}
