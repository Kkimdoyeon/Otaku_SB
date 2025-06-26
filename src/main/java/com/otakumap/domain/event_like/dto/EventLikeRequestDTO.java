package com.otakumap.domain.event_like.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class EventLikeRequestDTO {
    @Getter
    public static class FavoriteDTO {
        @NotNull(message = "즐겨찾기 여부 입력은 필수입니다.")
        Boolean isFavorite;
    }
}
