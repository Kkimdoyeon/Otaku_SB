package com.otakumap.domain.auth.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfo {
    private KakaoAccount kakao_account;

    @Getter
    public static class KakaoAccount {
        //private String name;
        private Profile profile;
        private String email;
    }

    @Getter
    public static class Profile {
        private String nickname;
    }
}