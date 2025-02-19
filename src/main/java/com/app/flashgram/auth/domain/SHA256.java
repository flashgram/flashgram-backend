package com.app.flashgram.auth.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 해시 알고리즘을 사용하여 텍스트를 해시화하는 유틸리티 클래스
 */
public class SHA256 {

    /**
     * 주어진 텍스트를 SHA-256 해시로 변환
     *
     * @param text 해시를 생성할 원본 텍스트
     * @return SHA-256 해시 값을 16진수 문자열로 반환
     * @throws IllegalArgumentException SHA-256 알고리즘을 찾을 수 없는 경우 발생
     */
    public static String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return byresToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("SHA-256 알고리즘을 찾을 수 없습니다.");
        }
    }

    /**
     * 주어진 바이트 배열을 16진수 문자열로 변환합
     *
     * @param bytes 변환할 바이트 배열
     * @return 변환된 16진수 문자열 반환
     */
    private static String byresToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private SHA256() {

    }
}
