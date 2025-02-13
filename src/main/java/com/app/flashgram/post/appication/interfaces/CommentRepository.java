package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.comment.Comment;

public interface CommentRepository {

    Comment save(Comment comment);
    Comment findById(Long id);
}
