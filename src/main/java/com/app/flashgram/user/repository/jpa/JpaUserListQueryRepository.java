package com.app.flashgram.user.repository.jpa;

import com.app.flashgram.user.application.dto.GetUserListResposeDto;
import com.app.flashgram.user.repository.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserListQueryRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT new com.app.flashgram.user.application.dto.GetUserListResposeDto(u.name, u.profileImageUrl) "
            + "FROM UserRelationEntity ur "
            + "INNER JOIN UserEntity u ON ur.followerUserId = u.id "
            + "WHERE ur.followingUserId = :userId")
    List<GetUserListResposeDto> getFollowUserList(Long userId);

    @Query(value = "SELECT new com.app.flashgram.user.application.dto.GetUserListResposeDto(u.name, u.profileImageUrl) "
            + "FROM UserRelationEntity ur "
            + "INNER JOIN UserEntity u ON ur.followingUserId = u.id "
            + "WHERE ur.followerUserId = :userId")
    List<GetUserListResposeDto> getFollowerUserList(Long userId);
}
