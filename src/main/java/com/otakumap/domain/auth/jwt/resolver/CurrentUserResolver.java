package com.otakumap.domain.auth.jwt.resolver;

import com.otakumap.domain.auth.jwt.annotation.CurrentUser;
import com.otakumap.domain.auth.jwt.userdetails.PrincipalDetails;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    private final UserQueryService userQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) && parameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            // principal이 PrincipalDetails 타입이 아니면 (문자열 "anonymousUser"인 경우) null 반환
            if (!(principal instanceof PrincipalDetails)) {
                return null;
            }
            PrincipalDetails principalDetails = (PrincipalDetails) principal;
            return userQueryService.getUserByEmail(principalDetails.getUsername());
        }
        return null;
    }
}