package com.app.flashgram.user.repository;

import com.app.flashgram.user.application.interfaces.UserRelationRepository;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.repository.entity.UserEntity;
import com.app.flashgram.user.repository.entity.UserRelationEntity;
import com.app.flashgram.user.repository.entity.UserRelationIdEntity;
import com.app.flashgram.user.repository.jpa.JpaUserRelationRepository;
import com.app.flashgram.user.repository.jpa.JpaUserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저 간의 팔로우 관계를 관리하는 Repository 구현체
 * 팔로우 관계의 생성, 삭제 및 존재 여부 확인 기능
 */
@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {

    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;

    /**
     * 특정 유저가 다른 유저를 이미 팔로우하고 있는지 확인
     *
     * @param user 팔로우를 하는 유저
     * @param targetUser 팔로우 대상 유저
     * @return 팔로우 관계 존재 여부 (true: 이미 팔로우 중, false: 팔로우하지 않음)
     */
    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());

        return jpaUserRelationRepository.existsById(id);
    }

    /**
     * 두 유저 간의 팔로우 관계 저장
     * 팔로우 관계 저장과 함께 양쪽 유저의 정보도 함께 업데이트
     *
     * @param user 팔로우를 하는 유저
     * @param targetUser 팔로우 대상 유저
     */
    @Override
    @Transactional
    public void save(User user, User targetUser) {
        UserRelationEntity entity = new UserRelationEntity(user.getId(), targetUser.getId());

        jpaUserRelationRepository.save(entity);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
    }

    /**
     * 두 유저 간의 팔로우 관계 삭제
     * 팔로우 관계 삭제와 함께 양쪽 유저의 정보도 함께 업데이트
     *
     * @param user 언팔로우를 하는 유저
     * @param targetUser 언팔로우 대상 유저
     */
    @Override
    @Transactional
    public void delete(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());

        jpaUserRelationRepository.deleteById(id);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
    }
}
