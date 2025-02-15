package com.otakumap.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

public class UserRequestDTO {
    @Getter
    public static class UpdateNicknameDTO {
        @NotBlank
        @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하로 입력해주세요.")
        private String nickname;
    }

    @Getter
    public static class UserReportRequestDTO {
        @NotBlank(message = "이벤트명을 입력해주세요.")
        private String eventName;

        @NotBlank(message = "애니메이션명을 입력해주세요.")
        private String animationName;

        private String additionalInfo;
    }

    @Getter
    public static class NotificationSettingsRequestDTO {
        @NotNull
        @Min(value = 1, message = "알림 타입은 1 또는 2로 입력해주세요.")
        @Max(value = 2, message = "알림 타입은 1 또는 2로 입력해주세요.")
        private Integer notificationType;

        @NotNull
        private boolean isEnabled;
    }

    @Getter
    public static class ResetPasswordDTO {
        @NotBlank(message = "아이디 입력은 필수 입니다.")
        String userId;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        @Schema(description = "password", example = "otakumap1234!")
        @Pattern(
                regexp = "^(?!.*(\\d)\\1{2})(?=(.*[A-Za-z]){1})(?=(.*\\d){1})(?!.*\\s).{10,}$|^(?!.*(\\d)\\1{2})(?=(.*[A-Za-z]){1})(?=(.*[^A-Za-z0-9]){1})(?!.*\\s).{10,}$|^(?!.*(\\d)\\1{2})(?=(.*\\d){1})(?=(.*[^A-Za-z0-9]){1})(?!.*\\s).{10,}$",
                message = "비밀번호는 영문, 숫자, 특수문자 중 2종류 이상을 조합하여 10자리 이상이어야 하며, 동일한 숫자 3개 이상을 연속해서 사용할 수 없습니다."
        )
        String password;

        @NotBlank(message = "비밀번호 재확인 입력은 필수입니다.")
        @Schema(description = "password", example = "otakumap1234!")
        String passwordCheck;
    }

    @Getter
    public static class ChangeEmailDTO {
        @NotBlank(message = "이메일 입력은 필수 입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email;
    }
}