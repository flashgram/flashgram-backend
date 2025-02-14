package com.app.flashgram.post.repository.post_queue;

import com.app.flashgram.post.repository.entity.post.PostEntity;

/**
 * 유저의 게시물 큐를 관리하는 Repository 인터페이스
 */
public interface UserPostQueueCommandRepository {

    /**
     * 새로운 게시물을 팔로워들의 피드 큐에 발행
     *
     * @param postEntity 발행할 게시물 엔티티
     */
    void publishPost(PostEntity postEntity);

    /**
     * 팔로우한 유저의 게시물들을 현재 유저의 피드 큐에 저장
     *
     * @param userId 현재 유저 ID
     * @param targetId 팔로우한 유저 ID
     */
    void saveFollowPost(Long userId, Long targetId);

    /**
     * 언팔로우한 유저의 게시물들을 현재 유저의 피드 큐에서 제거
     *
     * @param userId 현재 유저 ID
     * @param targetId 언팔로우한 유저 ID
     */
    void deleteUnfollowPost(Long userId, Long targetId);
}
