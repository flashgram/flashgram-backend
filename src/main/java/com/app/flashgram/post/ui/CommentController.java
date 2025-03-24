package com.app.flashgram.post.ui;

import com.app.flashgram.common.idempotency.Idempotent;
import com.app.flashgram.common.principal.AuthPrincipal;
import com.app.flashgram.common.principal.UserPrincipal;
import com.app.flashgram.common.ui.Response;
import com.app.flashgram.post.application.CommentService;
import com.app.flashgram.post.application.LikeService;
import com.app.flashgram.post.application.dto.CreateCommentRequestDto;
import com.app.flashgram.post.application.dto.LikeRequestDto;
import com.app.flashgram.post.application.dto.UpdateCommentRequestDto;
import com.app.flashgram.post.application.interfaces.CommentQueryRepository;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.ui.dto.GetCommentContentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 관련 API를 처리하는 컨트롤러
 * 댓글 생성, 수정, 좋아요 기능
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "댓글 API", description = "댓글 관련 API입니다.")
public class CommentController {

    private final CommentService commentService;
    private final LikeService likeService;
    private final CommentQueryRepository commentQueryRepository;

    /**
     * 새로운 댓글 생성
     *
     * @param dto 댓글 생성에 필요한 정보를 담은 DTO
     * @return 생성된 댓글의 ID
     */
    @Idempotent
    @PostMapping
    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
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
    @Idempotent
    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    public Response<Long> updateComment(@PathVariable(name = "commentId") Long commentId, @RequestBody UpdateCommentRequestDto dto) {
        Comment comment = commentService.updateComment(commentId, dto);

        return Response.ok(comment.getId());
    }

    /**
     * 댓글 삭제
     *
     * @param commentId 삭제할 댓글의 ID
     * @return void 응답
     */
    @Idempotent
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public Response<Void> deleteComment(@PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return Response.ok(null);
    }

    /**
     * 댓글에 좋아요 추가
     *
     * @param dto 좋아요 추가에 필요한 정보를 담은 DTO
     * @return void 응답
     */
    @Idempotent
    @PostMapping("/like")
    @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 추가합니다.")
    public Response<Void> likeComment(@RequestBody LikeRequestDto dto) {
        likeService.likeComment(dto);

        return Response.ok(null);
    }

    /**
     * 댓글의 좋아요 취소
     *
     * @param dto 좋아요 취소에 필요한 정보를 담은 DTO
     * @return void 응답
     */
    @Idempotent
    @PostMapping("/unlike")
    @Operation(summary = "댓글 좋아요 취소", description = "댓글의 좋아요를 취소합니다.")
    public Response<Void> unlikeComment(@RequestBody LikeRequestDto dto) {
        likeService.unlikeComment(dto);

        return Response.ok(null);
    }

    /**
     * 게시물에 대한 댓글 목록을 조회하는 API
     * 인증된 유저의 ID를 기반으로 댓글 목록을 조회하고, 해당 댓글의 정보를 응답으로 반환
     *
     * @param postId 조회할 게시물의 ID
     * @param userPrincipal 인증된 유저 정보
     * @param lastCommentId 마지막으로 조회한 댓글의 ID (optional)
     * @return 댓글 목록을 포함한 응답
     */
    @GetMapping("/{postId}")
    @Operation(summary = "댓글 목록 조회", description = "특정 게시물의 댓글 목록을 조회합니다.")
    public ResponseEntity<List<GetCommentContentResponseDto>> getCommentList(
            @PathVariable Long postId,
            @AuthPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false)Long lastCommentId) {
        List<GetCommentContentResponseDto> response = commentQueryRepository.findByPostId(postId, userPrincipal.getUserId(), lastCommentId);

        return ResponseEntity.ok(response);
    }
}
