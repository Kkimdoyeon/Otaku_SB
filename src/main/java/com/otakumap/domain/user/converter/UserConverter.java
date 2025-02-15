package com.otakumap.domain.user.converter;

import com.otakumap.domain.auth.dto.*;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.user.dto.UserResponseDTO;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user.entity.enums.Role;
import com.otakumap.domain.user.entity.enums.SocialType;
import com.otakumap.domain.user.entity.enums.UserStatus;
import com.otakumap.global.util.UuidGenerator;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static User toUser(AuthRequestDTO.SignupDTO request) {
        return User.builder()
                .name(request.getName())
                .nickname(UuidGenerator.generateUuid())
                .userId(request.getUserId())
                .email(request.getEmail())
                .password(request.getPassword())
                .isCommunityActivityNotified(true)
                .isEventBenefitsNotified(true)
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }

    public static AuthResponseDTO.SignupResultDTO toSignupResultDTO(User user) {
        return AuthResponseDTO.SignupResultDTO.builder()
                .id(user.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AuthResponseDTO.LoginResultDTO toLoginResultDTO(User user, String accessToken, String refreshToken) {
        return AuthResponseDTO.LoginResultDTO.builder()
                .id(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public static AuthResponseDTO.CheckIdResultDTO toCheckIdResultDTO(boolean isDuplicated) {
        return AuthResponseDTO.CheckIdResultDTO.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    public static AuthResponseDTO.CheckEmailResultDTO toCheckEmailResultDTO(boolean isDuplicated) {
        return AuthResponseDTO.CheckEmailResultDTO.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    public static AuthResponseDTO.VerifyCodeResultDTO toVerifyCodeResultDTO(boolean isVerified) {
        return AuthResponseDTO.VerifyCodeResultDTO.builder()
                .isVerified(isVerified)
                .build();
    }

    public static User toKakaoUser(KakaoUserInfo kakaoUserInfo) {
        return User.builder()
                .name(kakaoUserInfo.getKakao_account().getName())
                .nickname(UuidGenerator.generateUuid())
                .email(kakaoUserInfo.getKakao_account().getEmail())
                .socialType(SocialType.KAKAO)
                .build();
    }

    public static User toGoogleUser(GoogleUserInfo googleUserInfo) {
        return User.builder()
                .name(googleUserInfo.getName())
                .nickname(UuidGenerator.generateUuid())
                .email(googleUserInfo.getEmail())
                .socialType(SocialType.GOOGLE)
                .build();
    }

    public static User toNaverUser(NaverUserInfo naverUserInfo) {
        return User.builder()
                .name(naverUserInfo.getResponse().getName())
                .nickname(UuidGenerator.generateUuid())
                .email(naverUserInfo.getResponse().getEmail())
                .socialType(SocialType.NAVER)
                .build();
    }

    public static UserResponseDTO.UserInfoResponseDTO toUserInfoResponseDTO(User user) {
        return UserResponseDTO.UserInfoResponseDTO.builder()
                .profileImageUrl(user.getProfileImage() == null ? null : user.getProfileImage().getFileUrl())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .donation(user.getDonation())
                .community_activity(user.getIsCommunityActivityNotified())
                .event_benefits_info(user.getIsEventBenefitsNotified())
                .build();
    }

    public static AuthResponseDTO.FindIdResultDTO toFindIdResultDTO(String userId) {
        return AuthResponseDTO.FindIdResultDTO.builder()
                .userId(userId)
                .build();
    }

    public static UserResponseDTO.UserReviewDTO reviewDTO(PlaceReview review) {
        return UserResponseDTO.UserReviewDTO.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .thumbnail(review.getImages().get(0) == null ? null : review.getImages().get(0).getFileUrl()) // 이미지 여러 개면 수정 -> 나중에 수정 필요!
                .views(review.getView())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }

    public static UserResponseDTO.UserReviewListDTO reviewListDTO(Page<PlaceReview> reviews) {
        List<UserResponseDTO.UserReviewDTO> userReviewDTOS = reviews.stream()
                .map(UserConverter::reviewDTO).collect(Collectors.toList());

        return UserResponseDTO.UserReviewListDTO.builder()
                .reviews(userReviewDTOS)
                .listSize(userReviewDTOS.size())
                .totalPages(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .isFirst(reviews.isFirst())
                .isLast(reviews.isLast())
                .build();
    }
}
