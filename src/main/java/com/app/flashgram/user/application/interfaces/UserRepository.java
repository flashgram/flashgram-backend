package com.app.flashgram.user.application.interfaces;

import com.app.flashgram.user.domain.User;

public interface UserRepository {

    User save(User user);
    User findById(Long id);
}
