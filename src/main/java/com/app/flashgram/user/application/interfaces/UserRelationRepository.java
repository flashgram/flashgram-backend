package com.app.flashgram.user.application.interfaces;

import com.app.flashgram.user.domain.User;

public interface UserRelationRepository {

    boolean isAlreadyFollow(User user, User targetUser);
    void save(User user, User targetUser);
    void delete(User user, User targetUser);
}
