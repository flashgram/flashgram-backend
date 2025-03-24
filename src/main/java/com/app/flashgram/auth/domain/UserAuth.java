package com.app.flashgram.auth.domain;

/**
 * 유저 인증 정보를 나타내는 클래스
 * 유저의 이메일, 비밀번호, 권한 및 ID를 포함하여 인증 관련 기능을 제공
 */
public class UserAuth {

    private final Email email;
    private final Password password;
    private final UserRole userRole;
    private Long userId;

    public UserAuth(String email, String password, String role) {
        this.email = Email.createEmail(email);
        this.password = Password.creatEncryptPassword(password);
        this.userRole = UserRole.valueOf(role);
    }

    public UserAuth(String email, String password, String userRole, Long userId) {
        this.email = Email.createEmail(email);
        this.password = Password.createPassword(password);
        this.userRole = UserRole.valueOf(userRole);
        this.userId = userId;
    }

    public String getEmail() {

        return email.getEmailText();
    }

    public String getPassword() {

        return password.getEncryptPassword();
    }

    public String getUserRole() {

        return userRole.name();
    }

    public Long getUserId() {

        return userId;
    }

    /**
     * 주어진 비밀번호와 현재 비밀번호가 일치하는지 확인
     *
     * @param password 비밀번호 확인용
     * @return 비밀번호 일치 여부
     */
    public boolean matchPassword(String password) {

        return this.password.matchPassword(password);
    }
}
