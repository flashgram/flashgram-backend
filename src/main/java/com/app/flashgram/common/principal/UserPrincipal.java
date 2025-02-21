package com.app.flashgram.common.principal;

import com.app.flashgram.auth.domain.UserRole;
import lombok.Getter;

/**
 * 유저 정보를 담고 있는 주체 클래스
 * 인증된 유저의 ID와 권한 정보를 포함
 */
@Getter
public class UserPrincipal {

    private Long userId;
    private UserRole userRole;

    /**
     * UserPrincipal 객체를 생성하는 생성자
     *
     * @param userId 유저의 고유 ID
     * @param role 유저의 권한을 나타내는 문자열
     */
    public UserPrincipal(Long userId, String role) {
        this.userId = userId;
        this.userRole = UserRole.valueOf(role);
    }
}
