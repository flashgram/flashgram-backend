package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.Post;
import com.app.flashgram.user.domain.User;

public interface LIkeRepository {

    boolean checkLike(Post post, User user);
    void like(Post post, User user);
    void unlike(Post post, User user);
}
