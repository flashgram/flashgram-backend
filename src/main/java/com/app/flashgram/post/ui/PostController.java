package com.app.flashgram.post.ui;

import com.app.flashgram.common.ui.Response;
import com.app.flashgram.post.appication.PostService;
import com.app.flashgram.post.appication.dto.CreatePostRequestDto;
import com.app.flashgram.post.appication.dto.LikeRequestDto;
import com.app.flashgram.post.appication.dto.UpdatePostRequestDto;
import com.app.flashgram.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시물 관련 HTTP 요청을 처리하는 REST 컨트롤러
 * 게시물의 생성, 수정, 좋아요/좋아요 취소 기능
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 새로운 게시물 생성
     *
     * @param dto 게시물 생성에 필요한 정보를 담은 DTO
     * @return 생성된 게시물의 ID
     */
    @PostMapping
    public Response<Long> createPost(@RequestBody CreatePostRequestDto dto) {
        Post post = postService.createPost(dto);

        return Response.ok(post.getId());
    }

    /**
     * 기존 게시물의 내용 수정
     *
     * @param postId 수정할 게시물의 ID
     * @param dto 게시물 수정에 필요한 정보를 담은 DTO
     * @return 수정된 게시물의 ID
     */
    @PatchMapping("/{postId}")
    public Response<Long> updatePost(@PathVariable(name = "postId") Long postId, @RequestBody UpdatePostRequestDto dto) {
        Post post = postService.updatePost(postId, dto);

        return Response.ok(post.getId());
    }

    /**
     * 특정 게시물에 좋아요 추가
     * 이미 좋아요한 게시물인 경우 요청 무시
     *
     * @param dto 좋아요할 게시물과 유저 정보를 담은 DTO
     * @return 응답 객체 (데이터 없음)
     */
    @PostMapping("/like")
    public Response<Void> likePost(@RequestBody LikeRequestDto dto) {
        postService.likePost(dto);

        return Response.ok(null);
    }

    /**
     * 특정 게시물의 좋아요 취소
     * 좋아요하지 않은 게시물인 경우 요청 무시
     *
     * @param dto 좋아요를 취소할 게시물과 유저 정보를 담은 DTO
     * @return 응답 객체 (데이터 없음)
     */
    @PostMapping("/unlike")
    public Response<Void> unlikePost(@RequestBody LikeRequestDto dto) {
        postService.unlikePost(dto);

        return Response.ok(null);
    }
}
