package com.app.flashgram.auth.repositiry;

import com.app.flashgram.auth.appliction.interfaces.EmailSendRepository;
import com.app.flashgram.auth.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 이메일 발송을 처리하는 리포지토리 구현체
 * Gmail SMTP를 사용하여 인증 메일 발송
 */
@Repository
@RequiredArgsConstructor
public class EmailSendRepositoryImpl implements EmailSendRepository {

    @Override
    public void sendEmail(Email email, String token) {
        //TODO
        //Gmail SMTP 사용 예정
    }
}
