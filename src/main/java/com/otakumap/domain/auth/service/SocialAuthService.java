package com.otakumap.domain.auth.service;

import com.otakumap.domain.auth.dto.AuthRequestDTO;
import com.otakumap.domain.auth.dto.AuthResponseDTO;

public interface SocialAuthService {
    AuthResponseDTO.LoginResultDTO login(String provider, AuthRequestDTO.SocialLoginDTO request);
}
