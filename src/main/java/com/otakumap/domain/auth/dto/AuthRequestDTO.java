package com.otakumap.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class AuthRequestDTO {
    @Getter
    public static class SignupDTO {
        @NotBlank(message = "이름 입력은 필수입니다.")
        @Schema(description = "name", example = "오타쿠맵")
        String name;

        @NotBlank(message = "아이디 입력은 필수입니다.")
        @Schema(description = "userId", example = "otakumap1234")
        String userId;

        @NotBlank(message = "이메일 입력은 필수입니다.")
        @Schema(description = "email", example = "otakumap1234@gmail.com")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        @Schema(description = "password", example = "otakumap1234!")
        @Pattern(
                regexp = "^(?!.*(\\d)\\1{2})(?=(.*[A-Za-z]){1})(?=(.*\\d){1})(?!.*\\s).{10,}$|^(?!.*(\\d)\\1{2})(?=(.*[A-Za-z]){1})(?=(.*[^A-Za-z0-9]){1})(?!.*\\s).{10,}$|^(?!.*(\\d)\\1{2})(?=(.*\\d){1})(?=(.*[^A-Za-z0-9]){1})(?!.*\\s).{10,}$",
                message = "비밀번호는 영문, 숫자, 특수문자 중 2종류 이상을 조합하여 10자리 이상이어야 하며, 동일한 숫자 3개 이상을 연속해서 사용할 수 없습니다."
        )
        String password;

        @NotBlank(message = "비밀번호 재확인 입력은 필수입니다.")
        @Schema(description = "passwordCheck", example = "otakumap1234!")
        String passwordCheck;
    }

    @Getter
    public static class LoginDTO {
        @NotBlank(message = "아이디 입력은 필수입니다.")
        String userId;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        String password;
    }

    @Getter
    public static class VerifyEmailDTO {
        @NotBlank(message = "이메일 입력은 필수입니다.")
        String email;
    }

    @Getter
    public static class VerifyCodeDTO {
        @NotBlank(message = "인증 코드 입력은 필수입니다.")
        String code;

        @NotBlank(message = "이메일 입력은 필수입니다.")
        String email;
    }

    @Getter
    public static class FindPasswordDTO {
        @NotBlank(message = "이름 입력은 필수입니다.")
        String name;

        @NotBlank(message = "아이디 입력은 필수입니다.")
        String userId;
    }

    @Getter
    public static class SocialLoginDTO {
        @NotBlank(message = "인가 코드 입력은 필수입니다.")
        String code;
    }

    @Getter
    public static class VerifyResetCodeDTO {
        @NotBlank(message = "인증 코드 입력은 필수입니다.")
        String code;

        @NotBlank(message = "아이디 입력은 필수입니다.")
        String userId;
    }
}
