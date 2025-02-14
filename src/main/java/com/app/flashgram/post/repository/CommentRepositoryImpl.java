package com.app.flashgram.post.repository;

import com.app.flashgram.post.appication.interfaces.CommentRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.repository.entity.comment.CommentEntity;
import com.app.flashgram.post.repository.jpa.JpaCommentRepository;
import com.app.flashgram.post.repository.jpa.JpaPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 댓글 도메인 객체의 영속성 관리 Repository 구현체
 * JPA를 사용하여 데이터베이스와의 상호작용
 */
@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    private final JpaPostRepository jpaPostRepository;

    /**
     * 댓글을 저장하거나 업데이트
     * ID가 존재하는 경우 업데이트, 존재하지 않는 경우 새로 저장
     *
     * @param comment 저장할 댓글 객체
     * @return 저장된 댓글 객체
     */
    @Override
    @Transactional
    public Comment save(Comment comment) {

        Post targetPost = comment.getPost();
        CommentEntity commentEntity = new CommentEntity(comment);

        if (comment.getId() != null) {
            jpaCommentRepository.updateCommentEntitiy(commentEntity);
        }

        commentEntity = jpaCommentRepository.save(commentEntity);
        jpaPostRepository.increaseCommentCount(targetPost.getId());

        return commentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return commentEntity.toComment();
    }
}
