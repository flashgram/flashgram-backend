package com.app.flashgram.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 웹 관련 설정을 담당하는 클래스
 * CORS(Cross-Origin Resource Sharing) 설정 포함
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 설정을 추가하는 메소드
     *
     * @param registry CORS 설정을 등록할 레지스트리 객체
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
