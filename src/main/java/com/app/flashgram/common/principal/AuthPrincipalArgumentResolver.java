package com.app.flashgram.common.principal;

import com.app.flashgram.auth.domain.TokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * AuthPrincipal 애너테이션을 처리하는 메서드 인자 해석기
 * 요청의 인증 정보를 기반으로 사용자 정보를 주입
 */
public class AuthPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    public AuthPrincipalArgumentResolver(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * 지원하는 메서드 매개변수 확인
     *
     * @param parameter 메서드 매개변수 정보
     * @return AuthPrincipal 애너테이션이 있는 경우 true, 그렇지 않으면 false
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(AuthPrincipal.class);
    }

    /**
     * 인증된 사용자 정보를 해석하여 반환
     *
     * @param parameter 메서드 매개변수 정보
     * @param mavContainer 모델과 뷰 컨테이너
     * @param webRequest 웹 요청 정보
     * @param binderFactory 데이터 바인딩 팩토리
     * @return 사용자 정보를 담고 있는 UserPrincipal 객체
     * @throws IllegalArgumentException 인증 토큰이 유효하지 않은 경우 발생
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            String authToken = webRequest.getHeader("Authorization");

            if (authToken == null || authToken.split(" ").length != 2) {

                throw new IllegalArgumentException();
            }

            // Bearer [순수 토큰 값]
            String token = authToken.split(" ")[1];
            Long userId = tokenProvider.getUserId(token);
            String role = tokenProvider.getUserRole(token);

            return new UserPrincipal(userId, role);
        } catch (Exception e) {
            throw new IllegalArgumentException("올바르지 않은 토큰입니다..");
        }
    }
}
