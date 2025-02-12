package com.app.flashgram.user.repository.jpa;

import com.app.flashgram.user.application.dto.GetUserListResposeDto;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.app.flashgram.user.repository.entity.QUserRelationEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저 목록의 페이징 처리를 위한 QueryDSL Repository
 * 팔로워 목록 조회 등 복잡한 유저 관계 쿼리 처리
 */
@Repository
@RequiredArgsConstructor
public class JpaUserListPagingQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserEntity user = QUserEntity.userEntity;
    private static final QUserRelationEntity relation = QUserRelationEntity.userRelationEntity;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 특정 유저의 팔로워 목록을 페이징하여 조회
     * 한 번에 최대 20명의 팔로워를 조회하며, 팔로워 ID를 기준으로 내림차순 정렬
     *
     * @param userId 팔로워 목록을 조회할 유저의 ID
     * @param lastFollowerId 마지막으로 조회된 팔로워의 ID (첫 페이지 조회 시 null)
     * @return 팔로워 목록 DTO
     */
    public List<GetUserListResposeDto> getFollowerList(Long userId, Long lastFollowerId) {
        return jpaQueryFactory
                .select(
                        Projections.fields(
                                GetUserListResposeDto.class
                        )
                )
                .from(relation)
                .join(user).on(relation.followingUserId.eq(user.id))
                .where(
                        relation.followerUserId.eq(userId),
                        hasLastData(lastFollowerId)
                )
                .orderBy(user.id.desc())
                .limit(20)
                .fetch();
    }

    /**
     * 페이징 처리를 위한 동적 조건
     * 마지막으로 조회된 팔로워 ID보다 작은 ID를 가진 데이터만 조회
     *
     * @param lastId 마지막으로 조회된 팔로워의 ID
     * @return 동적 조건식. lastId가 null인 경우 null 반환
     */
    private BooleanExpression hasLastData(Long lastId) {

        if (lastId == null) {
            return null;
        }

        return user.id.lt(lastId);
    }
}
