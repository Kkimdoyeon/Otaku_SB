package com.otakumap.domain.auth.service;

import com.otakumap.domain.auth.dto.AuthRequestDTO;
import com.otakumap.domain.auth.dto.AuthResponseDTO;
import com.otakumap.domain.auth.jwt.dto.JwtDTO;
import com.otakumap.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthCommandService {
    User signup(AuthRequestDTO.SignupDTO request);
    AuthResponseDTO.LoginResultDTO login(AuthRequestDTO.LoginDTO request);
    void verifyEmail(AuthRequestDTO.VerifyEmailDTO request);
    boolean verifyCode(AuthRequestDTO.VerifyCodeDTO request);
    JwtDTO reissueToken(String refreshToken);
    void logout(HttpServletRequest request);
    void findPassword(AuthRequestDTO.FindPasswordDTO request);
    boolean verifyResetCode(AuthRequestDTO.VerifyResetCodeDTO request);
}
