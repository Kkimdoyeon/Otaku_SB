package com.otakumap.domain.auth.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otakumap.global.apiPayload.ApiResponse;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "FORBIDDEN_EXCEPTION_HANDLER")
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access Denied: ", accessDeniedException);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                ErrorStatus._FORBIDDEN.getReasonHttpStatus().getCode(),
                ErrorStatus._FORBIDDEN.getReasonHttpStatus().getMessage(),
                accessDeniedException.getMessage()
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}