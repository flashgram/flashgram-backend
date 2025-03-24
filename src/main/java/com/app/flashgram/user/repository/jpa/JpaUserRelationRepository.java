package com.app.flashgram.user.repository.jpa;

import com.app.flashgram.user.repository.entity.UserRelationEntity;
import com.app.flashgram.user.repository.entity.UserRelationIdEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {

    /**
     * 해당 유저가 팔로우하는 모든 유저의 ID 목록 조회
     * JPQL을 사용하여 팔로잉 ID만을 선택적으로 조회
     *
     * @param userId 팔로워 유저의 ID
     * @return 해당 유저가 팔로우하는 모든 유저의 ID 리스트
     */
    @Query(value = "SELECT u.followingUserId "
            + "FROM UserRelationEntity u "
            + "WHERE u.followerUserId = :userId")
    List<Long> findFollowers(Long userId);
}
