package com.app.flashgram.auth.domain;

import java.util.regex.Pattern;

/**
 * 이메일 주소를 나타내는 값 객체(Value Object)
 * 이메일 형식 검증 및 불변성 보장
 */
public class Email {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String email) {
        this.emailText = email;
    }

    /**
     * 이메일 객체 생성 팩토리 메서드
     *
     * @param email 이메일 주소 문자열
     * @return 생성된 이메일 객체
     * @throws IllegalArgumentException 이메일이 null이거나 공백인 경우, 이메일 형식이 올바르지 않은 경우
     */
    public static Email createEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일 주소는 필수 입력값입니다.");
        }

        if (isNotValidEmailString(email)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }

        return new Email(email);
    }

    public String getEmailText() {
        return this.emailText;
    }

    /**
     * 이메일 문자열이 유효한 형식인지 검증
     *
     * @param email 검증할 이메일 주소 문자열
     * @return 이메일이 유효하지 않으면 true, 유효하면 false
     */
    private static boolean isNotValidEmailString(String email) {
        return !pattern.matcher(email).matches();
    }
}
