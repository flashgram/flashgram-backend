package com.app.flashgram.auth.appliction.interfaces;

import com.app.flashgram.auth.domain.Email;

public interface EmailVerificationRepository {

    void createEmailVerification(Email email, String token);
    void verifyEmail(Email email, String token);
    boolean isEmailVerified(Email email);
}
