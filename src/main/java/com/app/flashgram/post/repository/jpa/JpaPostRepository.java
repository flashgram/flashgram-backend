package com.app.flashgram.post.repository.jpa;

import com.app.flashgram.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * PostEntity에 대한 JPA Repository 인터페이스
 * 게시물 엔티티의 기본적인 CRUD 작업과 커스텀 쿼리
 */
public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    /**
     * 게시물의 내용과 상태 업데이트
     * 업데이트 시 수정 시간(updAt)이 현재 시간으로 자동 설정
     *
     * @param postEntity 업데이트할 내용이 담긴 게시물 엔티티
     */
    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.content = :#{#postEntity.getContent()}, "
            + "p.state = :#{#postEntity.getState()}, "
            + "p.updAt = now() "
            + "WHERE p.id = :#{#postEntity.id}")
    void updatePostEntity(PostEntity postEntity);

    /**
     * 게시물의 좋아요 수 업데이트
     * 업데이트 시 수정 시간(updAt)이 현재 시간으로 자동 설정
     *
     * @param postEntity 업데이트할 좋아요 수가 담긴 게시물 엔티티
     */
    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.likeCount = :#{#postEntity.likeCount}, "
            + "p.updAt = now() "
            + "WHERE p.id = :#{#postEntity.getId()}")
    void updateLikeCount(PostEntity postEntity);
}
