package com.app.flashgram.auth.repositiry;

import com.app.flashgram.auth.appliction.interfaces.UserAuthRepository;
import com.app.flashgram.auth.domain.UserAuth;
import com.app.flashgram.auth.repositiry.entity.UserAuthEntity;
import com.app.flashgram.auth.repositiry.jpa.JpaUserAuthRepository;
import com.app.flashgram.notification.repository.JpaFcmTokenRepository;
import com.app.flashgram.notification.repository.entity.FcmTokenEntity;
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
    private final JpaFcmTokenRepository jpaFcmTokenRepository;

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

    /**
     * 유저의 로그인 기능을 처리
     *
     * @param email 유저의 이메일
     * @param password 유저의 비밀번호
     * @return 유저 인증 정보
     * @throws IllegalArgumentException 비밀번호가 일치하지 않을 경우 발생
     */
    @Override
    @Transactional
    public UserAuth loginUser(String email, String password, String fcmToken) {
        UserAuthEntity entity = jpaUserAuthRepository.findById(email).orElseThrow();
        UserAuth userAuth = entity.toUserAuth();

        if (!userAuth.matchPassword(password)) {

            throw new IllegalArgumentException("옳지 않은 비밀번호입니다.");
        }

        entity.updateLastLoginDt();
        jpaFcmTokenRepository.save(new FcmTokenEntity(userAuth.getUserId(), fcmToken));
        return userAuth;
    }
}
