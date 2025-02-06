package com.app.flashgram.user.application;

import com.app.flashgram.user.application.dto.FollowUserRequestDto;
import com.app.flashgram.user.application.interfaces.UserRelationRepository;
import com.app.flashgram.user.domain.User;

/**
 * 유저 간의 팔로우 관계를 관리 서비스 클래스
 * 팔로우 및 언팔로우 기능
 */
public class UserRelationService {

    private final UserService userService;
    private final UserRelationRepository userRelationRepository;

    public UserRelationService(UserService userService, UserRelationRepository userRelationRepository) {
        this.userService = userService;
        this.userRelationRepository = userRelationRepository;
    }

    /**
     * 특정 유저 팔로우
     *
     * @param dto 팔로우 요청에 필요한 정보(팔로우하는 유저 ID, 팔로우 대상 유저 ID)를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 이미 팔로우하고 있는 유저를 다시 팔로우하려 할 경우 발생
     * @throws IllegalArgumentException 존재하지 않는 유저 ID를 입력한 경우 발생
     */
    public void follow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException("이미 팔로우하고 있는 유저입니다.");
        }

        user.follow(targetUser);
        userRelationRepository.save(user, targetUser);
    }

    /**
     * 팔로우한 유저를 언팔로우
     *
     * @param dto 언팔로우 요청에 필요한 정보(언팔로우하는 유저 ID, 언팔로우 대상 유저 ID)를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 팔로우하지 않은 유저를 언팔로우하려 할 경우 발생
     * @throws IllegalArgumentException 존재하지 않는 유저 ID를 입력한 경우 발생
     */
    public void unfollow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException("팔로우하고 있지 않은 유저입니다.");
        }

        user.unfollow(targetUser);
        userRelationRepository.delete(user, targetUser);
    }
}
