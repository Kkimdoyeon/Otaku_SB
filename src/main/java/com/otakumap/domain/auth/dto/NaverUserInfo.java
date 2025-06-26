package com.otakumap.domain.auth.dto;

import lombok.Getter;

@Getter
public class NaverUserInfo {
    private Response response;

    @Getter
    public static class Response {
        private String name;
        private String email;
    }
}
