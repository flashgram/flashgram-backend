package com.app.flashgram.post.application.event;

import com.app.flashgram.post.application.interfaces.LikeRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 댓글 삭제 이벤트 리스너
 * 댓글 삭제 시 관련된 좋아요 삭제
 */
@Component
public class CommentDeleteEventListener {

    private final LikeRepository likeRepository;

    public CommentDeleteEventListener(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * 댓글 삭제 이벤트 처리
     *
     * @param event 댓글 삭제 이벤트
     */
    @EventListener
    public void handleCommentDeleteEvent(CommentDeleteEvent event) {
        Long commentId = event.getCommentId();

        likeRepository.unlikeAllByComment(commentId);
    }
}