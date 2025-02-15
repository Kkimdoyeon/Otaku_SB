package com.otakumap.domain.user.service;

import com.otakumap.domain.user.dto.UserRequestDTO;
import com.otakumap.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserCommandService {
    void updateNickname(User user, UserRequestDTO.UpdateNicknameDTO request);
    void reportEvent(UserRequestDTO.UserReportRequestDTO request);
    void updateNotificationSettings(User user, UserRequestDTO.NotificationSettingsRequestDTO request);
    void resetPassword(UserRequestDTO.ResetPasswordDTO request);
    String updateProfileImage(User user, MultipartFile file);
    void changeEmail(User user, UserRequestDTO.ChangeEmailDTO request);
}
