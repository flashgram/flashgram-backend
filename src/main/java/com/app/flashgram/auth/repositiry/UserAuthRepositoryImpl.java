package com.app.flashgram.auth.repositiry;

import com.app.flashgram.auth.appliction.interfaces.UserAuthRepository;
import com.app.flashgram.auth.domain.UserAuth;
import com.app.flashgram.auth.repositiry.entity.UserAuthEntity;
import com.app.flashgram.auth.repositiry.jpa.JpaUserAuthRepository;
import com.app.flashgram.user.application.interfaces.UserRepository;
import com.app.flashgram.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저 인증 정보를 관리하는 리포지토리 구현체
 * JPA를 사용하여 유저 인증 정보를 저장하고 관리
 */
@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepository jpaUserAuthRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 유저를 등록하고 인증 정보를 저장
     *
     * @param auth 유저 인증 정보
     * @param user 유저 정보
     * @return 저장된 유저 인증 정보
     */
    @Override
    @Transactional
    public UserAuth registerUser(UserAuth auth, User user) {
        User savedUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = new UserAuthEntity(auth, savedUser.getId());

        userAuthEntity = jpaUserAuthRepository.save(userAuthEntity);

        return userAuthEntity.toUserAuth();
    }
}
