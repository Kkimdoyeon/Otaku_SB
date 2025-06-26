package com.otakumap.domain.auth.service;

public interface AuthQueryService {
    boolean checkId(String userId);
    boolean checkEmail(String email);
    String findId(String name, String email);
}
