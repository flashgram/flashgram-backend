package com.app.flashgram.post.appication;

import com.app.flashgram.post.appication.dto.CreateCommentRequestDto;
import com.app.flashgram.post.appication.dto.LikeRequestDto;
import com.app.flashgram.post.appication.dto.UpdateCommentRequestDto;
import com.app.flashgram.post.appication.interfaces.CommentRepository;
import com.app.flashgram.post.appication.interfaces.LikeRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.ui.dto.GetCommentContentResponseDto;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 댓글 관련 비즈니스 로직 처리 서비스 클래스
 * 댓글의 생성, 수정 및 좋아요 기능 제공
 */
@Service
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public CommentService(UserService userService, PostService postService, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }
    /**
     * 주어진 댓글 ID를 통해 댓글을 조회
     *
     * @param id 조회할 댓글의 ID
     * @return 조회된 댓글 객체
     */
    public Comment getComment(Long id) {
        return commentRepository.findById(id);
    }

    /**
     * 게시물 ID와 유저 ID를 기준으로 댓글 목록을 조회
     * 또한, 마지막 댓글 ID(lastContentId)를 통해 페이지네이션을 지원
     *
     * @param postId 게시물의 ID
     * @param userId 댓글을 조회할 유저의 ID
     * @param lastContentId 마지막으로 조회된 댓글 ID (옵션)
     * @return 조회된 댓글 목록
     */
    public List<GetCommentContentResponseDto> getCommentList(Long postId, Long userId, Long lastContentId) {
        return commentRepository.findByPostId(postId, userId, lastContentId);
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
        return commentRepository.save(comment);
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
        return commentRepository.save(comment);
    }

    /**
     * 댓글에 좋아요 추가
     * 이미 좋아요가 있는 경우 아무 작업도 수행하지 않음
     *
     * @param dto 좋아요 추가에 필요한 정보를 담은 DTO
     */
    public void likeComment(LikeRequestDto dto) {
        Comment comment = getComment(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        likeRepository.like(comment, user);
    }

    /**
     * 댓글의 좋아요 취소
     * 좋아요가 없는 경우 아무 작업도 수행하지 않음
     *
     * @param dto 좋아요 취소에 필요한 정보를 담은 DTO
     */
    public void unlikeComment(LikeRequestDto dto) {
        Comment comment = getComment(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(comment, user)) {
            comment.unlike();
            likeRepository.unlike(comment, user);
        }
    }
}
