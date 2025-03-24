package com.app.flashgram.auth.repositiry.jpa;

import com.app.flashgram.auth.repositiry.entity.EmailVerificationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {

    Optional<EmailVerificationEntity> findByEmail(String email);
}
