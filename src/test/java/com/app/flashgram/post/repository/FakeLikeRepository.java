package com.app.flashgram.post.repository;

import com.app.flashgram.post.appication.interfaces.LikeRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.user.domain.User;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FakeLikeRepository implements LikeRepository {

    private final Map<Post, Set<User>> postLikes = new HashMap<>();
    private final Map<Comment, Set<User>> commentLikes = new HashMap<>();

    @Override
    public boolean checkLike(Post post, User user) {
        if (postLikes.get(post) == null) {
            return false;
        }

        return postLikes.get(post).contains(user);
    }

    @Override
    public void like(Post post, User user) {
        Set<User> users = postLikes.get(post);

        if (users == null) {
            users = new HashSet<>();
        }

        users.add(user);
        postLikes.put(post, users);
    }

    @Override
    public void unlike(Post post, User user) {
        Set<User> users = postLikes.get(post);

        if (users == null) {
           return;
        }

        users.remove(user);
        postLikes.put(post, users);
    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        if (commentLikes.get(comment) == null) {
            return false;
        }

        return commentLikes.get(comment).contains(user);
    }

    @Override
    public void like(Comment comment, User user) {
        Set<User> users = commentLikes.get(comment);

        if (users == null) {
            users = new HashSet<>();
        }

        users.add(user);
        commentLikes.put(comment, users);
    }

    @Override
    public void unlike(Comment comment, User user) {
        Set<User> users = commentLikes.get(comment);

        if (users == null) {
            return;
        }

        users.remove(user);
        commentLikes.put(comment, users);
    }
}
