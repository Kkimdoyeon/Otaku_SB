package com.otakumap.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class AuthRequestDTO {
    @Getter
    public static class SignupDTO {
        @NotBlank(message = "이름 입력은 필수입니다.")
        @Schema(description = "name", example = "오타쿠맵")
        String name;

        @NotBlank(message = "닉네임 입력은 필수입니다.")
        @Schema(description = "nickname", example = "오타쿠")
        String nickname;

        @NotBlank(message = "아이디 입력은 필수입니다.")
        @Schema(description = "userId", example = "otakumap1234")
        String userId;

        @NotBlank(message = "이메일 입력은 필수입니다.")
        @Schema(description = "email", example = "otakumap1234@gmail.com")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        @Schema(description = "password", example = "otakumap1234!")
        @Pattern(regexp = "^(?:(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,})|(?:(?=.*[a-zA-Z])(?=.*\\d).{10,})|(?:(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{10,})|(?:(?=.*\\d)(?=.*[!@#$%^&*]).{10,})$",
                message = "영문, 숫자, 특수문자 중 2종류 이상을 조합하여 최소 10자리 이상이거나, 영문, 숫자, 특수문자 모두를 포함하여 최소 8자리 이상 입력해야 합니다.")
        String password;

        @NotBlank(message = "비밀번호 재확인 입력은 필수 입니다.")
        @Schema(description = "passwordCheck", example = "otakumap1234!")
        String passwordCheck;
    }

    @Getter
    public static class LoginDTO {
        @NotNull
        String userId;
        @NotNull
        String password;
    }

    @Getter
    public static class CheckNicknameDTO {
        @NotNull
        String nickname;
    }

    @Getter
    public static class CheckIdDTO {
        @NotNull
        String userId;
    }

    @Getter
    public static class VerifyEmailDTO {
        @NotNull
        String email;
    }

    @Getter
    public static class VerifyCodeDTO {
        @NotNull
        String code;
        @NotNull
        String email;
    }

    @Getter
    public static class SocialLoginDTO {
        @NotNull
        String code;
    }
}
