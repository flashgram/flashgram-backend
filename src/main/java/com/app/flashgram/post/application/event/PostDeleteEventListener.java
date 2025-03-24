package com.app.flashgram.post.application.event;

import com.app.flashgram.post.application.interfaces.CommentCommandRepository;
import com.app.flashgram.post.application.interfaces.LikeRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 게시글 삭제 이벤트 리스너
 * 게시글 삭제 시 관련된 댓글과 좋아요 삭제
 */
@Component
public class PostDeleteEventListener {

    private final CommentCommandRepository commentCommandRepository;
    private final LikeRepository likeRepository;

    public PostDeleteEventListener(CommentCommandRepository commentCommandRepository, LikeRepository likeRepository) {
        this.commentCommandRepository = commentCommandRepository;
        this.likeRepository = likeRepository;
    }

    @EventListener
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        Long postId = event.getPostId();

        likeRepository.unlikeAllByPost(postId);
        likeRepository.unlikeAllByCommentsOfPost(postId);

        commentCommandRepository.deleteAllByPost(postId);
    }
}
