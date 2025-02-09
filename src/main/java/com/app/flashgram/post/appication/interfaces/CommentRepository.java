package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.comment.Comment;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
}
