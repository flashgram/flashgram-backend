package com.app.flashgram.auth.repositiry;

import com.app.flashgram.auth.appliction.interfaces.EmailVerificationRepository;
import com.app.flashgram.auth.domain.Email;
import com.app.flashgram.auth.repositiry.entity.EmailVerificationEntity;
import com.app.flashgram.auth.repositiry.jpa.JpaEmailVerificationRepository;
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
}
