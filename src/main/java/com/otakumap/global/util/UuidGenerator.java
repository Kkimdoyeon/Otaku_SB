package com.otakumap.global.util;

import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.AuthHandler;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UuidGenerator {
    public static String generateUuid() {
        // UUID를 문자열로 변환
        String uuid = UUID.randomUUID().toString();

        // 문자열을 UTF-8로 인코딩하여 바이트 배열로 변환
        byte[] uuidBytes = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] hash;
        try {
            // SHA-256 해시 함수를 사용하여 바이트 배열 해싱
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(uuidBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new AuthHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
        // 앞 4바이트를 16진수 문자열로 변환
        StringBuilder uniqueId = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            // 각 바이트를 2자리 16진수로 변환하여 결합
            uniqueId.append(String.format("%02x", hash[i]));
        }

        // 최종적으로 8자리 고유값 생성
        return uniqueId.toString();
    }
}
