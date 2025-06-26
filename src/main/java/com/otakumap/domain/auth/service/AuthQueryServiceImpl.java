package com.otakumap.domain.auth.service;

import com.otakumap.domain.user.repository.UserRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthQueryServiceImpl implements AuthQueryService {
    private final UserRepository userRepository;

    @Override
    public boolean checkId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String findId(String name, String email) {
        return userRepository.findByNameAndEmail(name, email).orElseThrow(() -> new AuthHandler(ErrorStatus.USER_NOT_FOUND)).getUserId();
    }
}
