package com.app.flashgram.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT 토큰을 생성하고 검증하는 클래스
 * 유저 ID 및 권한 정보를 포함하여 토큰을 관리
 */
@Component
public class TokenProvider {

    private final SecretKey key;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 1 hour

    public TokenProvider(@Value("${secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 유저 ID와 권한을 기반으로 JWT 토큰을 생성
     *
     * @param userId 유저 ID
     * @param role 유저 권한
     * @return 생성된 JWT 토큰
     */
    public String createToken(Long userId, String role) {
        Claims claims = Jwts.claims()
                .subject(userId.toString())
                .build();

        Date now = new Date();
        Date validateDate = new Date(now.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
                   .claims(claims)
                   .issuedAt(now)
                   .expiration(validateDate)
                   .claim("role", role)
                   .signWith(key)
                   .compact();
    }

    /**
     * JWT 토큰에서 유저 ID를 추출
     *
     * @param token JWT 토큰
     * @return 유저 ID
     */
    public Long getUserId(String token) {

        return Long.parseLong(
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()
        );
    }

    /**
     * JWT 토큰에서 유저 권한을 추출
     *
     * @param token JWT 토큰
     * @return 유저 권한
     */
    public String getUserRole(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
