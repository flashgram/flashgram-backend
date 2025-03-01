package com.app.flashgram.post.repository.post_queue;

import com.app.flashgram.post.repository.entity.like.QLikeEntity;
import com.app.flashgram.post.repository.entity.post.QPostEntity;
import com.app.flashgram.post.ui.dto.GetPostContentResponseDto;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 유저 피드의 게시물 데이터를 조회하는 UserPostQueueQueryRepository 구현 클래스
 * 유저 피드에 맞는 게시물 목록을 가져오기 위한 쿼리 처리
 */
@Repository
@RequiredArgsConstructor
public class UserPostQueueQueryRepositoryImpl implements UserPostQueueQueryRepository {

        private final RedisTemplate<String, Long> redisTemplate;
        private final JPAQueryFactory queryFactory;

        private static final QPostEntity postEntity = QPostEntity.postEntity;
        private static final QUserEntity userEntity = QUserEntity.userEntity;
        private static final QLikeEntity likeEntity = QLikeEntity.likeEntity;

        private static final String FEED_KEY_PREFIX = "user:feed:";

    /**
     * 유저 피드를 조회하여 게시물 데이터를 가져오는 메서드
     *
     * @param userId 유저 ID
     * @param lastContentId 마지막으로 조회된 게시물 ID
     * @return 조회된 게시물 데이터 리스트
     */
        @Override
        public List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastContentId) {
            String feedKey = FEED_KEY_PREFIX + userId;

            Long size = redisTemplate.opsForList().size(feedKey);

            if (size == null || size == 0) {
                return new ArrayList<>();
            }

            int startIndex = 0;

            if (lastContentId != null) {
                List<Long> allPostIds = redisTemplate.opsForList().range(feedKey, 0, -1);
                if (allPostIds != null) {
                    for (int i = 0; i < allPostIds.size(); i++) {
                        if (allPostIds.get(i).equals(lastContentId)) {
                            startIndex = i + 1;
                            break;
                        }
                    }
                }
            }

            List<Long> postIds = redisTemplate.opsForList().range(feedKey, startIndex, startIndex + 19);

            if (postIds == null || postIds.isEmpty()) {
                return new ArrayList<>();
            }

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
                    .from(postEntity)
                    .join(userEntity).on(postEntity.author.eq(userEntity))
                    .leftJoin(likeEntity).on(hasLike(userId))
                    .where(postEntity.id.in(postIds))
                    .orderBy(postEntity.id.desc())
                    .fetch();
        }

        /**
         * 해당 유저가 게시물에 대해 좋아요를 했는지 여부를 확인하는 메서드
         *
         * @param userId 유저 ID
         * @return 좋아요 여부 조건
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
}
