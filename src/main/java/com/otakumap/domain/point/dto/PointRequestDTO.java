package com.otakumap.domain.point.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class PointRequestDTO {
    @Getter
    public static class PointChargeDTO {
        @NotNull(message = "충전할 point 입력은 필수입니다.")
        Long point;
    }
}
