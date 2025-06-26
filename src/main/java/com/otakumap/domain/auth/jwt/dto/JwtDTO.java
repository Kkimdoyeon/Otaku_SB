package com.otakumap.domain.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtDTO {
    private String accessToken;
    private String refreshToken;
}
