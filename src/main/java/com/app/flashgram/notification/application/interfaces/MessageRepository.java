package com.app.flashgram.notification.application.interfaces;

import com.app.flashgram.user.domain.User;

public interface MessageRepository {

    void sendLikeMessage(User sender, User targetUser);
}
