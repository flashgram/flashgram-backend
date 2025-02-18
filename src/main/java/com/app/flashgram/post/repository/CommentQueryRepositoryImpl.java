package com.app.flashgram.post.repository;

import com.app.flashgram.post.appication.interfaces.CommentQueryRepository;
import com.app.flashgram.post.repository.entity.comment.QCommentEntity;
import com.app.flashgram.post.repository.entity.like.QLikeEntity;
import com.app.flashgram.post.ui.dto.GetCommentContentResponseDto;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final QCommentEntity commentEntity = QCommentEntity.commentEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;
    private static final QLikeEntity likeEntity = QLikeEntity.likeEntity;

    /**
     * 특정 게시물의 댓글 목록을 조회
     * 주어진 게시물 ID, 유저 ID, 마지막 댓글 ID를 기준으로 댓글 목록을 조회하며, 페이지네이션을 지원
     *
     * @param postId 게시물 ID
     * @param userId 유저 ID
     * @param lastCommentId 마지막 댓글 ID (옵션)
     * @return 댓글 목록
     */
    @Override
    public List<GetCommentContentResponseDto> findByPostId(Long postId, Long userId, Long lastCommentId) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetCommentContentResponseDto.class,
                                commentEntity.id.as("id"),
                                commentEntity.content.as("content"),
                                userEntity.id.as("userId"),
                                userEntity.name.as("userName"),
                                userEntity.profileImageUrl.as("userProfileImgUrl"),
                                commentEntity.regAt.as("createdAt"),
                                commentEntity.updAt.as("updatedAt"),
                                commentEntity.likeCount.as("likeCount"),
                                hasLike(userId).as("isLikedByMe"),
                                commentEntity.post.id.as("postId")
                        )
                )
                .from(commentEntity)
                .join(userEntity).on(commentEntity.author.id.eq(userEntity.id))
                .leftJoin(likeEntity).on(likeEntity.id.targetId.eq(commentEntity.id)
                                                               .and(likeEntity.id.targetType.eq("COMMENT"))
                                                               .and(likeEntity.id.userId.eq(userId)))
                .where(commentEntity.post.id.eq(postId), hasLastData(lastCommentId))
                .orderBy(commentEntity.regAt.desc())
                .limit(20)
                .fetch();
    }

    /**
     * 특정 유저가 댓글에 좋아요를 눌렀는지 여부를 체크하는 조건식 생성
     *
     * @param userId 유저 ID
     * @return 좋아요 여부를 나타내는 조건식
     */
    private BooleanExpression hasLike(Long userId) {
        if (userId == null) {
            return null;
        }
        return likeEntity.id.targetId.eq(commentEntity.id)
                                     .and(likeEntity.id.targetType.eq("COMMENT"))
                                     .and(likeEntity.id.userId.eq(userId));
    }

    /**
     * 마지막으로 조회된 댓글 ID를 기준으로 댓글 조회 범위를 설정하는 조건식 생성
     *
     * @param lastId 마지막 댓글 ID
     * @return 댓글 조회 범위 설정 조건식
     */
    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return commentEntity.id.lt(lastId);
    }
}
