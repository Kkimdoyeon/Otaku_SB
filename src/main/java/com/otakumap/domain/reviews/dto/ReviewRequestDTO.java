package com.otakumap.domain.reviews.dto;

import com.otakumap.domain.reviews.enums.ReviewType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.beans.Visibility;
import java.util.List;

public class ReviewRequestDTO {
    @Getter
    public static class CreateDTO {
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        @NotNull(message = "후기 종류를 입력해주세요. (place/event)")
        private ReviewType reviewType;

        @NotNull(message = "애니메이션 id를 입력해주세요.")
        private Long animeId;

        @NotBlank
        private Visibility visibility;

        @Size(min = 1, message = "루트 아이템은 최소 1개 이상 필요합니다.")
        private List<RouteDTO> routeItems;
    }

    @Getter
    public static class RouteDTO {
        @NotBlank(message = "장소 이름을 입력해주세요.")
        private String name;

        @NotNull
        private double lat;

        @NotNull
        private double lng;

        @NotBlank(message = "장소 설명을 입력해주세요.")
        private String detail;

        @NotNull(message = "order를 입력해주세요.")
        private Integer order;
    }

    public enum Visibility {
        PUBLIC, PURCHASERS_ONLY
    }
}
