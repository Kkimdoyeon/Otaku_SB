package com.otakumap.global.config;

import com.otakumap.domain.auth.jwt.filter.JwtFilter;
import com.otakumap.domain.auth.jwt.handler.JwtAccessDeniedHandler;
import com.otakumap.domain.auth.jwt.handler.JwtAuthenticationEntryPoint;
import com.otakumap.domain.auth.jwt.userdetails.PrincipalDetailsService;
import com.otakumap.domain.auth.jwt.util.JwtProvider;
import com.otakumap.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    private final PrincipalDetailsService principalDetailsService;

    private final String[] allowUrl = {
            "/",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/api/auth/**",
            "/api/users/reset-password/**"
    };

    private final String[] allowGetUrl = {
            "/api/events/**",
            "/api/reviews/**",
            "/api/places/**",
            "/api/routes/**",
            "/api/map/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //crsf 보안 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // spring security에서 걸리는 경우
                .cors(cors -> cors
                        .configurationSource(CorsConfig.corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrl).permitAll()
                        .requestMatchers(HttpMethod.GET, allowGetUrl).permitAll()
                        .anyRequest().authenticated())
                //기본 폼 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                // BasicHttp 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(new JwtFilter(jwtProvider, redisUtil, principalDetailsService), UsernamePasswordAuthenticationFilter.class)
                // 예외 처리 설정
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
