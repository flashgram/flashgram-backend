package com.app.flashgram.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.app.flashgram.fake.FakeObjectFactory;
import com.app.flashgram.user.application.dto.CreateUserRequestDto;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

/**
 * UserService 클래스의 기능을 검증하는 테스트 클래스
 * Fake 객체를 사용하여 유저 생성 및 조회 기능 테스트
 */
class UserServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();

    /**
     * 유저 생성 후 조회 기능을 테스트
     *
     * Given: 유저 정보가 담긴 DTO가 주어졌을 때
     * When: 유저를 생성하고
     * Then: 생성된 유저를 ID로 조회하면 동일한 정보를 가진 유저가 조회
     */
    @Test
    void givenUserInfoDto_whenCreateUser_thenCanFindUser() {
        //given
        CreateUserRequestDto dto = new CreateUserRequestDto("name1", "");

        //when
        User saveUser = userService.createUser(dto);

        //then
        User foundUser = userService.getUser(saveUser.getId());
        UserInfo userInfo = foundUser.getInfo();

        assertEquals(foundUser.getId(), saveUser.getId());
        assertEquals("name1", userInfo.getName());
    }
}
