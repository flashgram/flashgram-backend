package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.user.domain.User;

public interface LikeRepository {

    boolean checkLike(Post post, User user);
    void like(Post post, User user);
    void unlike(Post post, User user);

    boolean checkLike(Comment comment, User user);
    void like(Comment comment, User user);
    void unlike(Comment comment, User user);
}
