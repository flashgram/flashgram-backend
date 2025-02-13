package com.app.flashgram.post.repository;

import com.app.flashgram.post.appication.interfaces.LikeRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.repository.entity.like.LikeEntity;
import com.app.flashgram.post.repository.entity.post.PostEntity;
import com.app.flashgram.post.repository.jpa.JpaLikeRepository;
import com.app.flashgram.post.repository.jpa.JpaPostRepository;
import com.app.flashgram.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


/**
 * 게시물과 댓글의 좋아요 기능을 관리 Repository 구현체
 * JPA를 사용하여 좋아요 데이터의 영속성 관리
 */
@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final JpaLikeRepository jpaLikeRepository;
    private final JpaPostRepository jpaPostRepository;

    /**
     * 사용자가 특정 게시물 좋아요 여부 확인
     *
     * @param post 확인할 게시물
     * @param user 확인할 사용자
     * @return 좋아요를 눌렀다면 true, 아니면 false
     */
    @Override
    public boolean checkLike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);

        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    /**
     * 게시물에 좋아요 추가
     * 좋아요 엔티티를 저장하고 게시물의 좋아요 수 업데이트
     *
     * @param post 좋아요를 추가할 게시물
     * @param user 좋아요를 누른 사용자
     */
    @Override
    @Transactional
    public void like(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);

        jpaLikeRepository.save(new LikeEntity(post, user));
        jpaPostRepository.updateLikeCount(new PostEntity(post));
    }

    /**
     * 게시물의 좋아요 취소
     * 좋아요 엔티티를 삭제하고 게시물의 좋아요 수 업데이트
     *
     * @param post 좋아요를 취소할 게시물
     * @param user 좋아요를 취소한 사용자
     */
    @Override
    @Transactional
    public void unlike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);

        jpaLikeRepository.deleteById(likeEntity.getId());
        jpaPostRepository.updateLikeCount(new PostEntity(post));
    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        return false;
    }

    @Override
    public void like(Comment comment, User user) {

    }

    @Override
    public void unlike(Comment comment, User user) {

    }
}
