package com.app.flashgram.post.application.event;

/**
 * 게시글 삭제 이벤트
 */
public class PostDeleteEvent {

    private final Long postId;

    public PostDeleteEvent(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
