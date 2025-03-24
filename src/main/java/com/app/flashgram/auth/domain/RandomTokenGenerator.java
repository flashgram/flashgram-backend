package com.app.flashgram.auth.domain;

import java.security.SecureRandom;

/**
 * 랜덤 토큰을 생성하는 유틸리티 클래스
 * 영문자(대/소문자)와 숫자로 구성된 16자리 토큰 생성
 */
public class RandomTokenGenerator {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int TOKEN_LENGTH = 16;

    private static final SecureRandom random = new SecureRandom();

    /**
     * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자
     */
    private RandomTokenGenerator() {

    }

    /**
     * 16자리 랜덤 토큰 생성
     * SecureRandom을 사용하여 암호학적으로 안전한 난수 생성
     *
     * @return 생성된 랜덤 토큰 문자열
     */
    public static String generateToken() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);

        for (int i=0; i<16; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return token.toString();
    }
}
