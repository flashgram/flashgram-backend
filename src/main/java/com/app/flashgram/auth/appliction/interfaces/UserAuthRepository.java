package com.app.flashgram.auth.appliction.interfaces;

import com.app.flashgram.auth.domain.UserAuth;
import com.app.flashgram.user.domain.User;

public interface UserAuthRepository {

    UserAuth registerUser(UserAuth auth, User user);
    UserAuth loginUser(String email, String password, String fcmToken);
}
