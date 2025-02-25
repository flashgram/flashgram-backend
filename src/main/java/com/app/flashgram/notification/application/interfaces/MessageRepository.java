package com.app.flashgram.notification.application.interfaces;

import com.app.flashgram.user.domain.User;

public interface MessageRepository {

    void semdLikeMessage(User sender, User targetUser);
}
