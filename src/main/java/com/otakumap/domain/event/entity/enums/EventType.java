package com.otakumap.domain.event.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    POPUP_STORE("팝업 스토어"),
    EXHIBITION("전시회"),
    COLLABORATION_CAFE("콜라보 카페");

    private final String description;
}
