package com.app.flashgram.post.repository.jpa;

import com.app.flashgram.post.repository.entity.like.LikeEntity;
import com.app.flashgram.post.repository.entity.like.LikeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeIdEntity> {

    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.id.targetId = :postId AND l.id.targetType = 'POST'")
    void deleteByPostId(Long postId);

    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.id.targetId = :commentId AND l.id.targetType = 'COMMENT'")
    void deleteByCommentId(Long commentId);

    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.id.targetId IN (SELECT c.id FROM CommentEntity c WHERE c.post.id = :postId) AND l.id.targetType = 'COMMENT'")
    void deleteByCommentsOfPostId(Long postId);
}
