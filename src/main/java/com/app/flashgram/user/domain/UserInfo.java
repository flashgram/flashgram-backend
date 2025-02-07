package com.app.flashgram.user.domain;

/**
 * 유저의 기본 정보를 표현하는 값 객체
 */
public class UserInfo {

    private final String name;
    private final String profileImgUrl;

    /**
     * 유저 정보 값 객체를 생성
     * 이름은 필수값, null이나 빈 문자열이 입력되면 예외
     *
     * @param name          유저 이름
     * @param profileImgUrl 프로필 이미지 URL
     * @throws IllegalArgumentException 이름이 null이거나 빈 문자열인 경우
     */
    public UserInfo(String name, String profileImgUrl) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름은 null이거나 빈 문자열일 수 없습니다.");
        }

        this.name = name;
        this.profileImgUrl = profileImgUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }
}
