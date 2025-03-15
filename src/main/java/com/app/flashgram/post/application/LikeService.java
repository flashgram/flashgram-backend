package com.app.flashgram.post.application;

import com.app.flashgram.post.application.dto.LikeRequestDto;
import com.app.flashgram.post.application.interfaces.LikeRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.domain.User;
import org.springframework.stereotype.Service;

/**
 * 좋아요 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
public class LikeService {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final LikeRepository likeRepository;

    public LikeService(UserService userService, PostService postService,
                       CommentService commentService, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.likeRepository = likeRepository;
    }

    /**
     * 게시글에 좋아요 추가
     *
     * @param dto 좋아요 요청 정보
     */
    public void likePost(LikeRequestDto dto) {
        Post post = postService.getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(post, user)) {
            return;
        }

        post.like(user);
        likeRepository.like(post, user);
    }

    /**
     * 게시글 좋아요 취소
     *
     * @param dto 좋아요 취소 요청 정보
     */
    public void unlikePost(LikeRequestDto dto) {
        Post post = postService.getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(post, user)) {
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }

    /**
     * 댓글에 좋아요 추가
     *
     * @param dto 좋아요 요청 정보
     */
    public void likeComment(LikeRequestDto dto) {
        Comment comment = commentService.getComment(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        likeRepository.like(comment, user);
    }

    /**
     * 댓글 좋아요 취소
     *
     * @param dto 좋아요 취소 요청 정보
     */
    public void unlikeComment(LikeRequestDto dto) {
        Comment comment = commentService.getComment(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(comment, user)) {
            comment.unlike();
            likeRepository.unlike(comment, user);
        }
    }
}
