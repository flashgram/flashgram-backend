package com.app.flashgram.post.repository.jpa;

import com.app.flashgram.post.repository.entity.post.PostEntity;
import java.util.List;
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
     * @param postId
     * @param likeCount
     */
    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.likeCount = p.likeCount + :likeCount, "
            + "p.updAt = now() "
            + "WHERE p.id = :postId")
    void updateLikeCount(Long postId, Integer likeCount);

    /**
     * 게시물의 댓글 수를 1 증가시키고 수정 시간 업데이트
     *
     * @param id 댓글 수를 증가시킬 게시물의 ID
     */
    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.commentCount = p.commentCount + 1, "
            + "p.updAt = now() "
            + "WHERE p.id = :id")
    void increaseCommentCount(Long id);

    /**
     * 특정 작성자의 모든 게시물 ID를 조회
     *
     * @param authorId 게시물을 조회할 작성자의 ID
     * @return 해당 작성자가 작성한 모든 게시물의 ID 리스트
     */
    @Query("SELECT p.id FROM PostEntity p WHERE p.author.id = :authorId")
    List<Long> findAllPostIdByAuthorId(Long authorId);
}
