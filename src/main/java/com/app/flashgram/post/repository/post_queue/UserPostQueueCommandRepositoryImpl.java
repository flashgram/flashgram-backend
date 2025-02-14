package com.app.flashgram.post.repository.post_queue;

import com.app.flashgram.post.repository.entity.post.PostEntity;
import com.app.flashgram.post.repository.entity.post.UserPostQueueEntity;
import com.app.flashgram.post.repository.jpa.JpaPostRepository;
import com.app.flashgram.post.repository.jpa.JpaUserPostQueueRepository;
import com.app.flashgram.user.repository.entity.UserEntity;
import com.app.flashgram.user.repository.jpa.JpaUserRelationRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저의 게시물 큐를 관리하는 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository {

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserPostQueueRepository jpaUserPostQueueRepository;

    /**
     * 작성자의 모든 팔로워들의 피드 큐에 발행
     * 팔로워 목록을 조회하고 각 팔로워의 큐에 게시물 추가
     *
     * @param postEntity 발행할 게시물 엔티티
     */
    @Override
    @Transactional
    public void publishPost(PostEntity postEntity) {
        UserEntity userEntity = postEntity.getAuthor();

        List<Long> followers = jpaUserRelationRepository.findFollowers(userEntity.getId());
        List<UserPostQueueEntity> userPostQueueEntityList = followers.stream()
                                                                     .map(userId -> new UserPostQueueEntity(userId, postEntity.getId(), userEntity.getId()))
                                                                     .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    /**
     * 팔로우한 유저의 모든 게시물을 현재 유저의 피드 큐에 저장
     * 팔로우한 유저의 모든 게시물 ID를 조회하고 현재 유저의 큐에 추가
     *
     * @param userId 현재 유저 ID
     * @param targetId 팔로우한 유저 ID
     */
    @Override
    @Transactional
    public void saveFollowPost(Long userId, Long targetId) {
        List<Long> postIdList = jpaPostRepository.findAllPostIdByAuthorId(targetId);
        List<UserPostQueueEntity> userPostQueueEntityList = postIdList.stream()
                                                                      .map(postId -> new UserPostQueueEntity(userId, postId, targetId))
                                                                      .toList();
        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    /**
     * 언팔로우한 유저의 모든 게시물을 현재 유저의 피드 큐에서 제거
     *
     * @param userId 현재 유저 ID
     * @param targetId 언팔로우한 유저 ID
     */
    @Override
    @Transactional
    public void deleteUnfollowPost(Long userId, Long targetId) {
        jpaUserPostQueueRepository.deleteAllByUserIdAndAuthorId(userId, targetId);
    }
}
