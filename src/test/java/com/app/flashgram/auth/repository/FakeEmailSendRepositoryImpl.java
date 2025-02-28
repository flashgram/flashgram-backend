package com.app.flashgram.auth.repository;

import com.app.flashgram.auth.appliction.interfaces.EmailSendRepository;
import com.app.flashgram.auth.domain.Email;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class FakeEmailSendRepositoryImpl implements EmailSendRepository {

    private final Map<String, String> emailTokens = new HashMap<>();

    @Override
    public void sendEmail(Email email, String token) {
        emailTokens.put(email.getEmailText(), token);
    }

    public String getTokenForEmail(String email) {
        return emailTokens.get(email);
    }
}
