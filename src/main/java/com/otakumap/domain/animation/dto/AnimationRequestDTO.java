package com.otakumap.domain.animation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AnimationRequestDTO {
    @Getter
    public static class AnimationCreationRequestDTO {
        @NotBlank
        String name;
    }
}
