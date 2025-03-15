package com.app.flashgram.post.application.event;

/**
 * 댓글 삭제 이벤트
 */
public class CommentDeleteEvent {

    private final Long commentId;

    public CommentDeleteEvent(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
    }
}
