package com.app.flashgram.post.application;

import com.app.flashgram.post.application.dto.CreateCommentRequestDto;
import com.app.flashgram.post.application.dto.UpdateCommentRequestDto;
import com.app.flashgram.post.application.event.CommentDeleteEvent;
import com.app.flashgram.post.application.interfaces.CommentCommandRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 댓글 관련 비즈니스 로직 처리 서비스 클래스
 * 댓글의 생성, 수정 및 좋아요 기능 제공
 */
@Service
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentCommandRepository commentCommandRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CommentService(UserService userService, PostService postService,
                          CommentCommandRepository commentCommandRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.postService = postService;
        this.commentCommandRepository = commentCommandRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * 주어진 댓글 ID를 통해 댓글을 조회
     *
     * @param id 조회할 댓글의 ID
     * @return 조회된 댓글 객체
     */
    public Comment getComment(Long id) {
        return commentCommandRepository.findById(id);
    }

    /**
     * 새로운 댓글을 생성
     *
     * @param dto 댓글 생성에 필요한 정보를 담은 DTO
     * @return 생성된 댓글 객체
     */
    public Comment createComment(CreateCommentRequestDto dto) {
        Post post = postService.getPost(dto.postId());
        User user = userService.getUser(dto.userId());
        Comment comment = Comment.createComment(post, user, dto.content());

        return commentCommandRepository.save(comment);
    }

    /**
     * 기존 댓글 수정
     *
     * @param dto 댓글 수정에 필요한 정보를 담은 DTO
     * @return 수정된 댓글 객체
     */
    public Comment updateComment(Long commentId, UpdateCommentRequestDto dto) {
        Comment comment = getComment(commentId);
        User user = userService.getUser(dto.userId());

        comment.updateComment(user, dto.content());
        return commentCommandRepository.save(comment);
    }

    /**
     * 댓글 삭제
     *
     * @param commentId 삭제할 댓글의 ID
     */
    public void deleteComment(Long commentId) {

        eventPublisher.publishEvent(new CommentDeleteEvent(commentId));
        commentCommandRepository.delete(commentId);
    }
}
