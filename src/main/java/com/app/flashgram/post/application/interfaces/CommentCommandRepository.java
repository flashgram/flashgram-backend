package com.app.flashgram.post.application.interfaces;

import com.app.flashgram.post.domain.comment.Comment;

public interface CommentCommandRepository {

    Comment save(Comment comment);
    Comment findById(Long id);
    void delete(Long commentId);
    void deleteAllByPost(Long postId);
}
