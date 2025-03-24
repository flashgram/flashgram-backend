package com.app.flashgram.auth.domain;

/**
 * 비밀번호를 관리하는 도메인 클래스
 * SHA-256 암호화를 사용하여 비밀번호를 저장하고 검증
 */
public class Password {

    private final String encryptPassword;

    private Password(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    /**
     * 주어진 평문 비밀번호를 암호화하여 Password 객체 생성
     *
     * @param password 평문 비밀번호
     * @return 암호화된 Password 객체
     * @throws IllegalArgumentException 비밀번호가 null이거나 빈 문자열인 경우 발생
     */
    public static Password creatEncryptPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("패스워드는 빈 값이 될 수 없습니다.");
        }

        return new Password(SHA256.encrypt(password));
    }

    public static Password createPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("패스워드는 빈 값이 될 수 없습니다.");
        }

        return new Password(password);
    }

    /**
     * 주어진 평문 비밀번호가 현재 암호화된 비밀번호와 일치하는지 검증
     *
     * @param password 검증할 평문 비밀번호
     * @return 비밀번호 일치 여부
     */
    public boolean matchPassword(String password) {
        return encryptPassword.equals(SHA256.encrypt(password));
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }
}
