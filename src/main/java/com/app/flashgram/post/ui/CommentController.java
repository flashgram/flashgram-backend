package com.app.flashgram.post.ui;

import com.app.flashgram.common.ui.Response;
import com.app.flashgram.post.appication.CommentService;
import com.app.flashgram.post.appication.dto.CreateCommentRequestDto;
import com.app.flashgram.post.appication.dto.LikeRequestDto;
import com.app.flashgram.post.appication.dto.UpdateCommentRequestDto;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.ui.dto.GetCommentContentResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 관련 API를 처리하는 컨트롤러
 * 댓글 생성, 수정, 좋아요 기능
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글 생성
     *
     * @param dto 댓글 생성에 필요한 정보를 담은 DTO
     * @return 생성된 댓글의 ID
     */
    @PostMapping
    public Response<Long> createComment(@RequestBody CreateCommentRequestDto dto) {
        Comment comment = commentService.createComment(dto);

        return Response.ok(comment.getId());
    }

    /**
     * 기존 댓글 수정
     *
     * @param commentId 수정할 댓글의 ID
     * @param dto 댓글 수정에 필요한 정보를 담은 DTO
     * @return 수정된 댓글의 ID
     */
    @PatchMapping("/{commentId}")
    public Response<Long> updateComment(@PathVariable(name = "commentId") Long commentId, @RequestBody UpdateCommentRequestDto dto) {
        Comment comment = commentService.updateComment(commentId, dto);

        return Response.ok(comment.getId());
    }

    /**
     * 댓글에 좋아요 추가
     *
     * @param dto 좋아요 추가에 필요한 정보를 담은 DTO
     * @return void 응답
     */
    @PostMapping("/like")
    public Response<Void> likeComment(@RequestBody LikeRequestDto dto) {
        commentService.likeComment(dto);

        return Response.ok(null);
    }

    /**
     * 댓글의 좋아요 취소
     *
     * @param dto 좋아요 취소에 필요한 정보를 담은 DTO
     * @return void 응답
     */
    @PostMapping("/unlike")
    public Response<Void> unlikeComment(@RequestBody LikeRequestDto dto) {
        commentService.unlikeComment(dto);

        return Response.ok(null);
    }

    /**
     * 게시물에 대한 댓글 목록을 조회하는 API
     * 주어진 게시물 ID와 유저 ID를 기반으로 댓글 목록을 조회하고, 해당 댓글의 정보를 응답으로 반환
     *
     * @param postId 조회할 게시물의 ID
     * @param userId 댓글을 조회하는 유저의 ID
     * @param lastCommentId 마지막으로 조회한 댓글의 ID
     * @return 댓글 목록을 포함한 응답
     */
    @GetMapping("/{postId}/{userId}")
    public ResponseEntity<List<GetCommentContentResponseDto>> getCommentList(
            @PathVariable Long postId,
            @PathVariable(name = "userId") Long userId,
            Long lastCommentId) {
        List<GetCommentContentResponseDto> response = commentService.getCommentList(postId, userId, lastCommentId);
        return ResponseEntity.ok(response);
    }
}
