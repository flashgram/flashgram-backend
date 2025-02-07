package com.app.flashgram.user.repository;

import com.app.flashgram.user.application.interfaces.UserRepository;
import com.app.flashgram.user.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 테스트 목적의 인메모리 유저 저장소 구현체
 * HashMap을 사용하여 메모리에 유저 정보를 저장하고 관리
 */
public class FakeUserRepository implements UserRepository {

    private final Map<Long, User> store = new HashMap<>();

    /**
     * 유저 정보를 저장
     * 유저 ID가 없는 경우 새로운 ID를 생성하여 저장하고,
     * ID가 있는 경우 해당 ID로 저장
     *
     * @param user 저장할 유저 객체
     * @return 저장된 유저 객체 (새로 생성된 경우 ID 할당)
     */
    @Override
    public User save(User user) {
        if (user.getId() != null ) {
            store.put(user.getId(), user);
        }

        Long id = store.size() + 1L;
        User newUser = new User(id, user.getInfo());

        store.put(id, newUser);

        return newUser;
    }

    /**
     * ID로 유저를 조회
     *
     * @param id 조회할 유저의 ID
     * @return 조회된 유저 객체를 포함한 Optional
     *         유저가 존재하지 않는 경우 빈 Optional 반환
     */
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
