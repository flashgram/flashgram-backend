package com.app.flashgram.user.repository;

import com.app.flashgram.user.application.interfaces.UserRepository;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.repository.jpa.JpaUserRepository;
import com.app.flashgram.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저 정보의 영속성을 관리하는 Repository 구현체
 * JPA를 이용하여 유저 정보를 데이터베이스에 저장 및 조회
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    /**
     * 유저 정보 저장
     * 도메인 객체를 엔티티로 변환하여 저장한 후, 다시 도메인 객체로 변환하여 반환
     *
     * @param user 저장할 유저 도메인 객체
     * @return 저장된 유저 도메인 객체
     */
    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        entity = jpaUserRepository.save(entity);

        return entity.toUser();
    }

    /**
     * ID로 유저 정보 조회
     *
     * @param id 조회할 유저의 ID
     * @return 조회된 유저 도메인 객체
     * @throws IllegalArgumentException 해당 ID의 유저가 존재하지 않을 경우 발생
     */
    @Override
    public User findById(Long id) {
        UserEntity entity = jpaUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return entity.toUser();
    }
}
