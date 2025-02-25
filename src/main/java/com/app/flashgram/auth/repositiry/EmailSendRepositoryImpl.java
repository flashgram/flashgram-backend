package com.app.flashgram.auth.repositiry;

import com.app.flashgram.auth.appliction.interfaces.EmailSendRepository;
import com.app.flashgram.auth.domain.Email;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


/**
 * 이메일 발송을 처리하는 리포지토리 구현체
 * Gmail SMTP를 사용하여 인증 메일 발송
 */
@Repository
@RequiredArgsConstructor
public class EmailSendRepositoryImpl implements EmailSendRepository {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    /**
     * 이메일 인증 메일 전송
     *
     * @param email 인증을 받을 이메일 정보
     * @param token 인증 토큰으로, 이메일 내용에 포함되어 발송
     * @throws RuntimeException 이메일 전송 중 문제가 발생한 경우 발생
     */
    @Override
    public void sendEmail(Email email, String token) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getEmailText()));
            message.setSubject("[Flashgram] 이메일 인증 요청");
            message.setText("인증 토큰: " + token + "\n이메일 인증을 완료하려면 해당 토큰을 입력하세요.");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
    }
}
