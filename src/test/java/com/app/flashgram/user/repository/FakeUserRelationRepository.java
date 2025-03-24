package com.app.flashgram.user.repository;

import com.app.flashgram.user.application.interfaces.UserRelationRepository;
import com.app.flashgram.user.domain.User;
import java.util.HashSet;
import java.util.Set;

/**
 * 테스트 목적의 인메모리 유저간 관계 저장소 구현체
 * HashSet에 사용하여 메모리에 팔로우/언팔로우 기능 테스트
 */
public class FakeUserRelationRepository implements UserRelationRepository {

    private final Set<Relation> store = new HashSet<>();

    /**
     * 한 유저가 다른 유저를 이미 팔로우하고 있는지 확인
     * @param user 팔로우를 했을 수 있는 유저
     * @param targetUser 팔로우를 받았을 수 있는 유저
     * @return user가 targetUser를 팔로우하고 있으면 true, 아니면 false
     */
    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        return store.contains(new Relation(user.getId(), targetUser.getId()));
    }

    /**
     * 두 유저 간의 새로운 팔로우 관계를 생성
     *
     * @param user 팔로우를 하는 유저
     * @param targetUser 팔로우를 받는 유저
     */
    @Override
    public void save(User user, User targetUser) {
        store.add(new Relation(user.getId(), targetUser.getId()));
    }

    /**
     * 두 유저 간의 팔로우 관계를 제거
     *
     * @param user 언팔로우를 하는 유저
     * @param targetUser 언팔로우를 당하는 유저
     */
    @Override
    public void delete(User user, User targetUser) {
        store.remove(new Relation(user.getId(), targetUser.getId()));
    }
}

record Relation(Long userId, Long targetUserId) { }