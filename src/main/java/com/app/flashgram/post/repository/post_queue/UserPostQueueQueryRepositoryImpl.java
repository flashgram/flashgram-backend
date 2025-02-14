package com.app.flashgram.post.repository.post_queue;

import com.app.flashgram.post.repository.entity.like.QLikeEntity;
import com.app.flashgram.post.repository.entity.post.QPostEntity;
import com.app.flashgram.post.repository.entity.post.QUserPostQueueEntity;
import com.app.flashgram.post.ui.dto.GetPostContentResponseDto;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저 피드의 게시물 데이터를 조회하는 UserPostQueueQueryRepository 구현 클래스
 * 유저 피드에 맞는 게시물 목록을 가져오기 위한 쿼리 처리
 */
@Repository
@RequiredArgsConstructor
public class UserPostQueueQueryRepositoryImpl implements UserPostQueueQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserPostQueueEntity userPostQueueEntity = QUserPostQueueEntity.userPostQueueEntity;
    private static final QPostEntity postEntity = QPostEntity.postEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;
    private static final QLikeEntity likeEntity = QLikeEntity.likeEntity;

    /**
     * 유저의 게시물 피드를 조회
     *
     * @param userId 조회할 유저의 ID
     * @param lastContentId 마지막으로 본 게시물의 ID
     */
    public List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastContentId) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetPostContentResponseDto.class,
                                postEntity.id.as("id"),
                                postEntity.content.as("content"),
                                userEntity.id.as("userId"),
                                userEntity.name.as("userName"),
                                userEntity.profileImageUrl.as("userProfileImgUrl"),
                                postEntity.regAt.as("createdAt"),
                                postEntity.updAt.as("updatedAt"),
                                postEntity.commentCount.as("commentCount"),
                                postEntity.likeCount.as("likeCount"),
                                likeEntity.isNotNull().as("isLikedByMe")
                        )
                )
                .from(userPostQueueEntity)
                .join(postEntity).on(userPostQueueEntity.postId.eq(postEntity.id))
                .join(userEntity).on(userPostQueueEntity.authorId.eq(userEntity.id))
                .leftJoin(likeEntity).on(hasLike(userId))
                .where(
                        userPostQueueEntity.userId.eq(userId),
                        hasLastData(lastContentId)
                )
                .orderBy(userPostQueueEntity.postId.desc())
                .limit(20)
                .fetch();
    }

    /**
     * 유저가 해당 게시물을 좋아요 했는지 여부를 확인하는 조건
     *
     * @param userId 유저의 ID
     * @return 유저가 좋아요한 게시물인지 여부를 판단하는 조건식
     */
    private BooleanExpression hasLike(Long userId) {
        if (userId == null) {

            return null;
        }

        return postEntity.id
                .eq(likeEntity.id.targetId)
                .and(likeEntity.id.targetType.eq("POST"))
                .and(likeEntity.id.userId.eq(userId));
    }

    /**
     * 마지막으로 본 게시물 ID가 존재하면 해당 게시물 ID보다 작은 게시물만 조회하는 조건
     *
     * @param lastId 마지막 게시물 ID
     * @return 마지막 게시물 ID 기준으로 필터링하는 조건식
     */
    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null) {

            return null;
        }

        return postEntity.id.lt(lastId);
    }
}
