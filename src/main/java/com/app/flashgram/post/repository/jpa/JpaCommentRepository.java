package com.app.flashgram.post.repository.jpa;

import com.app.flashgram.post.repository.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * CommentEntity에 대한 JPA Repository 인터페이스
 * 댓글 엔티티의 기본적인 CRUD 작업과 커스텀 쿼리
 */
public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {

    /**
     * 댓글 내용 업데이트
     * 업데이트 시 수정 시간(updAt)이 현재 시간으로 자동 설정
     *
     * @param comment 업데이트할 내용이 담긴 댓글 엔티티
     */
    @Modifying
    @Query(value = "UPDATE CommentEntity c "
            + "SET c.content = :#{#comment.getContent()}, "
            + "c.updAt = now() "
            + "WHERE c.id = :#{#comment.getId()}")
    void updateCommentEntitiy(CommentEntity comment);

    /**
     * 댓글의 좋아요 수 업데이트
     * 업데이트 시 수정 시간(updAt)이 현재 시간으로 자동 설정
     *
     * @param commentEntity 업데이트할 좋아요 수가 담긴 댓글 엔티티
     */
    @Modifying
    @Query(value = "UPDATE CommentEntity c "
            + "SET c.likeCount = :#{#commentEntity.likeCount}, "
            + "c.updAt = now() "
            + "WHERE c.id = :#{#commentEntity.getId()}")
    void updateLikeCount(CommentEntity commentEntity);
}
