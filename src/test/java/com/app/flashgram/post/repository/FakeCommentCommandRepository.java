package com.app.flashgram.post.repository;

import com.app.flashgram.post.appication.interfaces.CommentCommandRepository;
import com.app.flashgram.post.domain.comment.Comment;
import java.util.HashMap;
import java.util.Map;

public class FakeCommentCommandRepository implements CommentCommandRepository {

    private Map<Long, Comment> store = new HashMap<>();

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() != null) {
            store.put(comment.getId(), comment);

            return comment;
        }

        long id = store.size();
        Comment newComment = new Comment(id, comment.getPost(), comment.getAuthor(), comment.getContentObject());
        store.put(id, newComment);

        return newComment;
    }

    @Override
    public Comment findById(Long id) {
        return store.get(id);
    }
}
