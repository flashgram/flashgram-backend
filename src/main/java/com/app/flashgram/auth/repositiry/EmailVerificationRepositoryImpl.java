package com.app.flashgram.auth.repositiry;

import com.app.flashgram.auth.appliction.interfaces.EmailVerificationRepository;
import com.app.flashgram.auth.domain.Email;
import com.app.flashgram.auth.repositiry.entity.EmailVerificationEntity;
import com.app.flashgram.auth.repositiry.jpa.JpaEmailVerificationRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 이메일 인증 정보를 관리하는 리포지토리 구현체
 * JPA를 사용하여 이메일 인증 정보를 저장하고 관리
 */
@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    /**
     * 이메일 인증 정보를 생성하거나 토큰 갱신
     * 이미 인증된 이메일인 경우 예외가 발생
     *
     * @param email 인증할 이메일 주소
     * @param token 인증 토큰
     * @throws IllegalArgumentException 이미 인증된 이메일인 경우
     */
    @Override
    @Transactional
    public void createEmailVerification(Email email, String token) {
        String emailAddress = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRepository.findByEmail(emailAddress);

        if (entity.isPresent()) {
            EmailVerificationEntity emailVerificationEntity = entity.get();

            if (emailVerificationEntity.isVerified()) {
                throw new IllegalArgumentException("이미 인증된 이메일입니다.");
            }

            emailVerificationEntity.updateToken(token);

            return;
        }

        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailAddress, token);

        jpaEmailVerificationRepository.save(emailVerificationEntity);
    }

    /**
     * 이메일과 토큰을 검증하여 이메일 인증
     * 다음의 경우에 예외가 발생합니다:
     * - 인증을 요청하지 않은 이메일인 경우
     * - 이미 인증된 이메일인 경우
     * - 토큰이 일치하지 않는 경우
     *
     * @param email 인증할 이메일 주소
     * @param token 인증 토큰
     * @throws IllegalArgumentException 인증 실패 시 발생 (미요청 이메일, 이미 인증됨, 잘못된 토큰)
     */
    @Override
    @Transactional
    public void verifyEmail(Email email, String token) {
        String emailAddress = email.getEmailText();

        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(emailAddress)
                                                                       .orElseThrow(() -> new IllegalArgumentException("인증을 요청하지 않은 이메일입니다."));

        if (entity.isVerified()) {
            throw new IllegalArgumentException("이미 인증된 이메일입니다.");
        }

        if (!entity.hasSameToken(token)) {
            throw new IllegalArgumentException("토큰 값이 유효하지 않습니다.");
        }

        entity.verify();
    }

    /**
     * 주어진 이메일의 인증 완료 여부 확인
     *
     * @param email 확인할 이메일 주소
     * @return 이메일 인증 완료 여부
     * @throws IllegalArgumentException 인증을 요청하지 않은 이메일인 경우 발생
     */
    @Override
    public boolean isEmailVerified(Email email) {
        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(email.getEmailText())
                                                                         .orElseThrow(() -> new IllegalArgumentException("인증을 요청하지 않은 이메일입니다."));
        return entity.isVerified();
    }
}
