package com.app.flashgram.post.repository;

import com.app.flashgram.post.application.interfaces.PostRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.repository.entity.post.PostEntity;
import com.app.flashgram.post.repository.jpa.JpaPostRepository;
import com.app.flashgram.post.repository.post_queue.UserPostQueueCommandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Post 도메인 객체의 영속성 관리 Repository 구현체
 * JPA를 사용하여 데이터베이스와의 상호작용
 */
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository jpaPostRepository;
    private final UserPostQueueCommandRepository commandRepository;

    /**
     * Post 객체 저장하거나 업데이트
     *
     * @param post 저장할 Post 객체
     * @return 저장된 Post 객체
     */
    @Override
    @Transactional
    public Post save(Post post) {
        PostEntity postEntity = new PostEntity(post);

        if (postEntity.getId() != null) {
            jpaPostRepository.updatePostEntity(postEntity);

            return postEntity.toPost();
        }

        postEntity = jpaPostRepository.save(postEntity);
        commandRepository.publishPost(postEntity);

        return postEntity.toPost();
    }

    /**
     * 게시글 삭제
     *
     * @param postId 삭제할 게시글의 ID
     */
    @Override
    @Transactional
    public void delete(Long postId) {

        jpaPostRepository.deletePostById(postId);
    }

    /**
     * 게시글 조회
     *
     * @param id 조회할 게시글의 ID
     * @return 조회된 Post 객체
     * @throws IllegalArgumentException 해당 ID의 게시글이 존재하지 않는 경우 예외 발생
     */
    @Override
    public Post findById(Long id) {
        PostEntity entity = jpaPostRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return entity.toPost();
    }
}
