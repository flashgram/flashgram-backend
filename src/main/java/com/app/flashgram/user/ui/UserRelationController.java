package com.app.flashgram.user.ui;

import com.app.flashgram.common.ui.Response;
import com.app.flashgram.user.application.UserRelationService;
import com.app.flashgram.user.application.dto.FollowUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 간의 관계(팔로우/언팔로우) 관리 REST 컨트롤러
 * 유저 간의 팔로우 관계 생성 및 해제 기능
 */
@RestController
@RequestMapping("/relation")
@RequiredArgsConstructor
public class UserRelationController {

    private final UserRelationService relationService;

    /**
     * 특정 유저 팔로우
     * 이미 팔로우한 유저인 경우 요청 무시
     *
     * @param dto 팔로우할 유저와 팔로우를 요청하는 유저의 정보를 담은 DTO
     * @return 응답 객체 (데이터 없음)
     */
    @PostMapping("/follow")
    public Response<Void> followUser(@RequestBody FollowUserRequestDto dto) {
        relationService.follow(dto);

        return Response.ok(null);
    }

    /**
     * 특정 유저를 언팔로우
     * 팔로우하지 않은 유저인 경우 요청 무시
     *
     * @param dto 언팔로우할 유저와 언팔로우를 요청하는 유저의 정보를 담은 DTO
     * @return 응답 객체 (데이터 없음)
     */
    @PostMapping("/unfollow")
    public Response<Void> unfollowUser(@RequestBody FollowUserRequestDto dto) {
        relationService.unfollow(dto);

        return Response.ok(null);
    }
}
