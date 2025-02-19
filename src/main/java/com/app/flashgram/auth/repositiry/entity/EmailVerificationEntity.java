package com.app.flashgram.auth.repositiry.entity;

import com.app.flashgram.common.repository.entity.TimeBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fg_email_verification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailVerificationEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    private boolean isVerified;

    public EmailVerificationEntity(String email, String token) {
        this.id = null;
        this.email = email;
        this.token = token;
        this.isVerified = false;
    }

    /**
     * 현재 이메일의 인증 완료 여부 반환
     *
     * @return 이메일 인증 완료 여부
     */
    public boolean isVerified() {
        return isVerified;
    }

    /**
     * 인증 토큰을 새로운 값으로 업데이트
     *
     * @param token 새로운 인증 토큰
     */
    public void updateToken(String token) {
        this.token = token;
    }

    /**
     * 이메일 인증을 완료 처리
     * 인증 상태를 true로 변경합니다.
     */
    public void verify() {
        this.isVerified = true;
    }

    /**
     * 주어진 토큰과 현재 저장된 토큰이 일치하는지 확인
     *
     * @param token 검증할 토큰
     * @return 토큰 일치 여부
     */
    public boolean hasSameToken(String token) {
        return this.token.equals(token);
    }
}
