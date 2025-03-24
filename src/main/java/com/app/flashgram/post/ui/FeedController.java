package com.app.flashgram.post.ui;

import com.app.flashgram.common.principal.AuthPrincipal;
import com.app.flashgram.common.principal.UserPrincipal;
import com.app.flashgram.common.ui.Response;
import com.app.flashgram.post.repository.post_queue.UserPostQueueQueryRepository;
import com.app.flashgram.post.ui.dto.GetPostContentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 피드 관련 HTTP 요청을 처리하는 REST 컨트롤러
 * 유저가 볼 수 있는 게시물 목록을 제공
 */
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
@Tag(name = "피드 API", description = "피드 관련 API입니다.")
public class FeedController {

    private final UserPostQueueQueryRepository queueQueryRepository;

    /**
     * 유저의 게시물 피드를 조회
     *
     * @param userPrincipal 조회할 유저의 ID
     * @param lastPostId 마지막으로 본 게시물의 ID (optional)
     * @return 유저의 게시물 피드를 담은 응답 객체
     */
    @GetMapping
    @Operation(summary = "피드 조회", description = "자신이 팔로우한 유저의 게시글을 피드에서 조회합니다.")
    public Response<List<GetPostContentResponseDto>> getPostFeed(@AuthPrincipal UserPrincipal userPrincipal, Long lastPostId) {
        List<GetPostContentResponseDto> result = queueQueryRepository.getContentResponse(userPrincipal.getUserId(), lastPostId);

        return Response.ok(result);
    }
}
