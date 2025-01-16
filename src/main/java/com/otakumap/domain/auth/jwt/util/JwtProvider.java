package com.otakumap.domain.auth.jwt.util;

import com.otakumap.domain.auth.jwt.dto.JwtDTO;
import com.otakumap.domain.auth.jwt.userdetails.PrincipalDetails;
import com.otakumap.domain.auth.jwt.userdetails.PrincipalDetailsService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.AuthHandler;
import com.otakumap.global.util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtProvider {
    private final PrincipalDetailsService userDetailsService;
    private final SecretKey secret;
    private final Long accessExpiration;
    private final Long refreshExpiration;
    private final RedisUtil redisUtil;

    public JwtProvider(
            PrincipalDetailsService userDetailsService,
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.token.access-expiration-time}") Long accessExpiration,
            @Value("${spring.jwt.token.refresh-expiration-time}") Long refreshExpiration,
            RedisUtil redisUtil) {
        this.userDetailsService = userDetailsService;
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.redisUtil = redisUtil;
    }

    // AccessToken 생성
    public String createAccessToken(PrincipalDetails userDetails) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(accessExpiration);

        return Jwts.builder()
                .setHeader(Map.of("alg", "HS256", "typ", "JWT"))
                .setSubject(userDetails.getUsername())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiredAt))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    // RefreshToken 생성
    public String createRefreshToken(PrincipalDetails userDetails) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(refreshExpiration);

        String refreshToken = Jwts.builder()
                .setHeader(Map.of("alg", "HS256", "typ", "JWT"))
                .setSubject(userDetails.getUsername())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiredAt))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
        redisUtil.set(userDetails.getUsername(), refreshToken);
        redisUtil.expire(userDetails.getUsername(), refreshExpiration, TimeUnit.MILLISECONDS);
        return refreshToken;
    }

    // 헤더에서 토큰 추출
    public String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.split(" ")[1];
    }

    // AccessToken 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            return claims.getBody().getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException e) {
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage() + ": 토큰이 유효하지 않습니다.");
            return false;
        }
    }

    // RefreshToken 유효성 확인
    public void validateRefreshToken(String refreshToken) {
        String username = getUserId(refreshToken);

        //redis 확인
        if (!redisUtil.exists(username)) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }
    }

    //userId 추출
    public String getUserId(String token) {
        return getClaims(token).getBody().getSubject();
    }

    //토큰의 클레임 가져오는 메서드
    public Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }
    }

    // 토큰 재발급
    public JwtDTO reissueToken(String refreshToken) throws SignatureException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId(refreshToken));

        return new JwtDTO(
                createAccessToken((PrincipalDetails) userDetails),
                createRefreshToken((PrincipalDetails)userDetails)
        );
    }

    // 토큰 유효시간 반환
    public Long getExpTime(String token) {
        return getClaims(token).getPayload().getExpiration().getTime();
    }
}