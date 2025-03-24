package com.app.flashgram.auth.appliction.interfaces;

import com.app.flashgram.auth.domain.Email;

public interface EmailSendRepository {

    void sendEmail(Email email, String token);
}
