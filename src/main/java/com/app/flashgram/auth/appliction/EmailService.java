package com.app.flashgram.auth.appliction;

import com.app.flashgram.auth.appliction.dto.SendEmailRequestDto;
import com.app.flashgram.auth.appliction.interfaces.EmailSendRepository;
import com.app.flashgram.auth.appliction.interfaces.EmailVerificationRepository;
import com.app.flashgram.auth.domain.Email;
import com.app.flashgram.auth.domain.RandomTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 이메일 인증 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSendRepository emailSendRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    /**
     * 인증 이메일을 발송하고 인증 정보 저장
     *
     * @param dto 이메일 발송에 필요한 정보를 담은 DTO
     */
    public void sendEmail(SendEmailRequestDto dto) {
        Email email = Email.createEmail(dto.email());
        String token = RandomTokenGenerator.generateToken();

        emailSendRepository.sendEmail(email, token);
        emailVerificationRepository.createEmailVerification(email, token);
    }
}
