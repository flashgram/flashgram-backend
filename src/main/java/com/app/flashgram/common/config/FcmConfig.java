package com.app.flashgram.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Firebase Cloud Messaging 관련 설정을 담당하는 클래스
 * FCM 자격 증명을 설정하고 애플리케이션 초기화
 */
@Slf4j
@Component
public class FcmConfig {

    /**
     * FCM 자격 증명 파일 경로
     */
    @Value("${fcm.certification}")
    private String fcmApplicationCredentials;

    /**
     * 애플리케이션 시작 시 FirebaseApp 초기화
     *
     * 클래스패스에서 자격 증명 파일을 로드하고 Firebase 옵션을 설정한 후,
     * 애플리케이션이 이미 초기화되지 않았다면 FirebaseApp 초기화
     * 초기화 과정에서 오류가 발생하면 로그에 기록
     */
    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource resource = new ClassPathResource(fcmApplicationCredentials);

            try (InputStream is = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                                                         .setCredentials(GoogleCredentials.fromStream(is))
                                                         .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                    log.info("FirebaseApp initialized!");
                }
            }
        } catch (Exception e) {
            log.error("FirebaseApp 초기화 실패: {}", e.getMessage(), e);
        }
    }
}
