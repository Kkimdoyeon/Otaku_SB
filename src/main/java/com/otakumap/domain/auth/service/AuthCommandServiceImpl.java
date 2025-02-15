package com.otakumap.domain.auth.service;

import com.otakumap.domain.auth.dto.AuthRequestDTO;
import com.otakumap.domain.auth.dto.AuthResponseDTO;
import com.otakumap.domain.auth.jwt.dto.JwtDTO;
import com.otakumap.domain.auth.jwt.userdetails.PrincipalDetails;
import com.otakumap.domain.auth.jwt.util.JwtProvider;
import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.image.repository.ImageRepository;
import com.otakumap.domain.user.converter.UserConverter;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user.repository.UserRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.AuthHandler;
import com.otakumap.global.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private final ImageRepository imageRepository;

    @Override
    public User signup(AuthRequestDTO.SignupDTO request) {
        if(!request.getPassword().equals(request.getPasswordCheck())) {
            throw new AuthHandler(ErrorStatus.PASSWORD_NOT_EQUAL);
        }
        User newUser = UserConverter.toUser(request);
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));

        // 기본 이미지는 항상 PK값을 1로 가져오도록 설정
        Image profileImage = imageRepository.findById(1L).orElseThrow(() -> new AuthHandler(ErrorStatus.IMAGE_NOT_FOUND));
        newUser.setProfileImage(profileImage);
        return userRepository.save(newUser);
    }

    @Override
    public AuthResponseDTO.LoginResultDTO login(AuthRequestDTO.LoginDTO request) {
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new AuthHandler(ErrorStatus.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthHandler(ErrorStatus.PASSWORD_NOT_EQUAL);
        }

        PrincipalDetails memberDetails = new PrincipalDetails(user);

        // 로그인 성공 시 토큰 생성
        String accessToken = jwtProvider.createAccessToken(memberDetails, user.getId());
        String refreshToken = jwtProvider.createRefreshToken(memberDetails, user.getId());

        return UserConverter.toLoginResultDTO(user, accessToken, refreshToken);
    }

    @Override
    public void verifyEmail(AuthRequestDTO.VerifyEmailDTO request) {
        try {
            mailService.sendEmail(request.getEmail(), "signup");
        } catch (MessagingException e) {
            throw new AuthHandler(ErrorStatus.EMAIL_WRITE_FAILED);
        } catch (MailException e) {
            throw new AuthHandler(ErrorStatus.EMAIL_SEND_FAILED);
        }
    }

    @Override
    public boolean verifyCode(AuthRequestDTO.VerifyCodeDTO request) {
        String authCode = (String) redisUtil.get("auth:" + request.getEmail() + ":" + "signup");
        if (authCode == null) {
            throw new AuthHandler(ErrorStatus.EMAIL_CODE_EXPIRED);
        }
        if (!authCode.equals(request.getCode())) {
            throw new AuthHandler(ErrorStatus.CODE_NOT_EQUAL);
        }
        return true;
    }

    @Override
    public JwtDTO reissueToken(String refreshToken) {
        try {
            jwtProvider.validateRefreshToken(refreshToken);
            return jwtProvider.reissueToken(refreshToken);
        } catch (ExpiredJwtException eje) {
            throw new AuthHandler(ErrorStatus.TOKEN_EXPIRED);
        } catch (IllegalArgumentException iae) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            String accessToken = jwtProvider.resolveAccessToken(request);
            // 블랙리스트에 저장
            redisUtil.set(accessToken, "logout");
            redisUtil.expire(accessToken, jwtProvider.getExpTime(accessToken), TimeUnit.MILLISECONDS);
            // RefreshToken 삭제
            redisUtil.delete(jwtProvider.getEmail(accessToken));
        } catch (ExpiredJwtException e) {
            throw new AuthHandler(ErrorStatus.TOKEN_EXPIRED);
        }
    }

    @Override
    public void findPassword(AuthRequestDTO.FindPasswordDTO request) {
        User user = userRepository.findByNameAndUserId(request.getName(), request.getUserId()).orElseThrow(() -> new AuthHandler(ErrorStatus.USER_NOT_FOUND));
        try {
            mailService.sendEmail(user.getEmail(), "findPassword");
        } catch (MessagingException e) {
            throw new AuthHandler(ErrorStatus.EMAIL_WRITE_FAILED);
        } catch (MailException e) {
            throw new AuthHandler(ErrorStatus.EMAIL_SEND_FAILED);
        }
    }

    @Override
    public boolean verifyResetCode(AuthRequestDTO.VerifyResetCodeDTO request) {
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new AuthHandler(ErrorStatus.USER_NOT_FOUND));
        String authCode = (String) redisUtil.get("auth:" + user.getEmail() + ":" + "findPassword");
        if (authCode == null) {
            throw new AuthHandler(ErrorStatus.EMAIL_CODE_EXPIRED);
        }
        if (!authCode.equals(request.getCode())) {
            throw new AuthHandler(ErrorStatus.CODE_NOT_EQUAL);
        }
        return true;
    }
}
