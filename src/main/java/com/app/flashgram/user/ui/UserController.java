package com.app.flashgram.user.ui;

import com.app.flashgram.common.idempotency.Idempotent;
import com.app.flashgram.common.ui.Response;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.application.dto.CreateUserRequestDto;
import com.app.flashgram.user.application.dto.GetUserListResposeDto;
import com.app.flashgram.user.application.dto.GetUserResponseDto;
import com.app.flashgram.user.application.dto.UpdateUserRequestDto;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.repository.jpa.JpaUserListQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 관련 HTTP 요청 REST 컨트롤러
 * 유저 생성, 프로필 조회, 팔로워/팔로잉 목록 조회 등
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JpaUserListQueryRepository userListQueryRepository;

    /**
     * 새로운 유저 생성
     *
     * @param dto 유저 생성에 필요한 정보를 담은 DTO
     * @return 생성된 유저의 ID
     */
    @Idempotent
    @PostMapping
    public Response<Long> createUser(@RequestBody CreateUserRequestDto dto) {
        User user = userService.createUser(dto);

        return Response.ok(user.getId());
    }

    /**
     * 유저 정보를 업데이트하고 업데이트된 유저의 ID를 반환하는 메서드
     *
     * @param userId 수정할 유저의 ID
     * @param dto 유저 정보 업데이트에 필요한 정보를 담고 있는 DTO 객체
     * @return 업데이트된 유저의 ID를 담은 Response 객체
     */
    @Idempotent
    @PatchMapping("/{userId}")
    public Response<Long> updateUser(@PathVariable(name = "userId") Long userId,
                                    @RequestBody UpdateUserRequestDto dto) {
        User updatedUser = userService.updateUser(userId, dto);
        return Response.ok(updatedUser.getId());
    }

    /**
     * 특정 유저의 프로필 정보 조회
     *
     * @param userId 조회할 유저의 ID
     * @return 유저 프로필 정보
     */
    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUserProfile(@PathVariable(name = "userId") Long userId) {
        return Response.ok(userService.getUserProfile(userId));
    }

    /**
     * 특정 유저의 팔로워 목록 조회
     * 팔로워는 해당 유저를 팔로우하는 다른 유저
     *
     * @param userId 팔로워 목록을 조회할 유저의 ID
     * @return 팔로워 목록 (각 팔로워의 이름과 프로필 이미지 URL 포함)
     */
    @GetMapping("/{userId}/follower")
    public Response<List<GetUserListResposeDto>> getFollowerList(@PathVariable(name = "userId") Long userId) {
        List<GetUserListResposeDto> result = userListQueryRepository.getFollowerUserList(userId);

        return Response.ok(result);
    }

    /**
     * 특정 유저의 팔로잉 목록 조회
     * 팔로잉은 해당 유저가 팔로우하는 다른 유저
     *
     * @param userId 팔로잉 목록을 조회할 유저의 ID
     * @return 팔로잉 목록 (각 팔로잉 유저의 이름과 프로필 이미지 URL 포함)
     */
    @GetMapping("/{userId}/following")
    public Response<List<GetUserListResposeDto>> getFollowingList(@PathVariable(name = "userId") Long userId) {
        List<GetUserListResposeDto> result = userListQueryRepository.getFollowingUserList(userId);

        return Response.ok(result);
    }
}
