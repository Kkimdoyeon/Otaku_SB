package com.otakumap.domain.user.service;

import com.otakumap.domain.user.dto.UserResponseDTO;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface UserQueryService {
    User getUserByEmail(String email);
    User getUserInfo(Long userId);
    Page<UserResponseDTO.UserReviewDTO> getMyReviews(User user, Integer page, String sort);
}
