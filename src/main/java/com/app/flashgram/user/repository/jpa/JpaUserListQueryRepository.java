package com.app.flashgram.user.repository.jpa;

import com.app.flashgram.user.application.dto.GetUserListResposeDto;
import com.app.flashgram.user.repository.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 유저 목록 조회를 위한 JPA Repository 인터페이스
 * JPQL을 사용하여 팔로우/팔로워 관계의 유저 목록 조회
 */
public interface JpaUserListQueryRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 특정 유저가 팔로우하는 유저 목록 조회
     * 조회된 결과는 유저 이름과 프로필 이미지 URL
     *
     * @param userId 팔로우 목록을 조회할 유저의 ID
     * @return 팔로우하는 유저들의 정보가 담긴 DTO 리스트
     */
    @Query(value = "SELECT new com.app.flashgram.user.application.dto.GetUserListResposeDto(u.name, u.profileImageUrl) "
            + "FROM UserRelationEntity ur "
            + "INNER JOIN UserEntity u ON ur.followerUserId = u.id "
            + "WHERE ur.followingUserId = :userId")
    List<GetUserListResposeDto> getFollowingUserList(Long userId);

    /**
     * 특정 유저를 팔로우하는 유저 목록(팔로워)을 조회
     * 조회된 결과는 유저 이름과 프로필 이미지 URL
     *
     * @param userId 팔로워 목록을 조회할 유저의 ID
     * @return 팔로워들의 정보가 담긴 DTO 리스트
     */
    @Query(value = "SELECT new com.app.flashgram.user.application.dto.GetUserListResposeDto(u.name, u.profileImageUrl) "
            + "FROM UserRelationEntity ur "
            + "INNER JOIN UserEntity u ON ur.followingUserId = u.id "
            + "WHERE ur.followerUserId = :userId")
    List<GetUserListResposeDto> getFollowerUserList(Long userId);
}
