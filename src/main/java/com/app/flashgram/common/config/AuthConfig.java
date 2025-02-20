package com.app.flashgram.common.config;

import com.app.flashgram.auth.domain.TokenProvider;
import com.app.flashgram.common.principal.AuthPrincipalArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인증 관련 설정을 담당하는 클래스
 * AuthPrincipal 애너테이션을 처리하기 위한 ArgumentResolver를 등록
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    public final TokenProvider tokenProvider;

    public AuthConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * 유저 정의 ArgumentResolver를 추가하는 메서드
     *
     * @param argumentResolvers 추가할 ArgumentResolver 리스트
     */
    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(new AuthPrincipalArgumentResolver(tokenProvider));
    }
}
