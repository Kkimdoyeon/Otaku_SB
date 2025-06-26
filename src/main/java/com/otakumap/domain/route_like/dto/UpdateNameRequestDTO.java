package com.otakumap.domain.route_like.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateNameRequestDTO(
        @NotBlank(message = "제목은 빈칸이 불가합니다.")
        @Size(max = 50, message = "제목은 50자 이하여야 합니다.")
        String name
) {}
