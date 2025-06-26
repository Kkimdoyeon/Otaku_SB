package com.otakumap.domain.image.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ImageRequestDTO {
    @Getter
    public static class uploadDTO {
        @NotBlank
        String folder;
    }
}
