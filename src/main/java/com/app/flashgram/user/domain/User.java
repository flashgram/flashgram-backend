package com.app.flashgram.user.domain;

import com.app.flashgram.common.domain.PositiveIntegerCounter;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 유저 도메인 객체
 */
@Getter
@Builder
@AllArgsConstructor
public class User {

    private Long id;
    private UserInfo userInfo;
    private PositiveIntegerCounter followingCount;
    private PositiveIntegerCounter followerCounter;

    /**
     * 유저 도메인 객체 생성
     *
     * @param id       유저 식별자
     * @param userInfo 유저 정보 값 객체
     */
    public User(Long id, UserInfo userInfo) {
        if (userInfo == null) {
            throw new IllegalArgumentException("유저 정보는 null일 수 없습니다.");
        }

        this.id = id;
        this.userInfo = userInfo;
        this.followingCount = new PositiveIntegerCounter();
        this.followerCounter = new PositiveIntegerCounter();
    }

    public User(String name, String profileImgUrl) {
        this(null, new UserInfo(name, profileImgUrl));
    }

    /**
     * 유저 이름 업데이트
     *
     * 유저의 이름 업데이트
     *
     * @param name 새 유저 이름
     */
    public void updateName(String name) {
        if (name != null) {
            this.userInfo = new UserInfo(name, this.userInfo.getProfileImgUrl());
        }
    }

    /**
     * 유저 프로필 이미지 업데이트
     *
     * 유저의 프로필 이미지 업데이트
     *
     * @param profileImg 새 프로필 이미지 URL
     */
    public void updateProfileImg(String profileImg) {
        if (profileImg != null) {
            this.userInfo = new UserInfo(this.userInfo.getName(), profileImg);
        }
    }

    /**
     * 지정된 유저 팔로우
     * 팔로우 시 자신의 팔로잉 수와 대상 유저의 팔로워 수가 증가
     *
     * @param targetUser 팔로우할 대상 유저
     * @throws IllegalArgumentException 자기 자신을 팔로우하려는 경우
     */
    public void follow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new IllegalArgumentException("자신을 팔로우 할 수 없습니다.");
        }

        followingCount.increase();
        targetUser.increaseFollowerCount();
    }

    /**
     * 지정된 유저 언팔로우
     * 언팔로우 시 자신의 팔로잉 수와 대상 유저의 팔로워 수가 감소
     *
     * @param targetUser 언팔로우할 대상 유저
     * @throws IllegalArgumentException 자기 자신을 언팔로우하려는 경우
     */
    public void unfollow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new IllegalArgumentException("자신을 언팔로우 할 수 없습니다.");
        }

        followingCount.decrease();
        targetUser.decreaseFollowerCount();
    }

    private void increaseFollowerCount() {
        followerCounter.increase();
    }

    private void decreaseFollowerCount() {
        followerCounter.decrease();
    }

    /**
     * 유저 식별자 값을 기준으로 동일성을 비교
     * 두 유저 객체의 식별자가 같다면 같은 유저로 판단
     *
     * @param o 비교 대상 객체
     * @return 같은 식별자를 가진 유저인 경우 true
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    /**
     * 유저의 식별자를 기반으로 해시값을 생성
     *
     * @return 유저 식별자의 해시값
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int followerCount() {
        return followerCounter.getCount();
    }

    public int followingCount() {
        return followingCount.getCount();
    }

    public String getName() {
        return userInfo.getName();
    }

    public String getProfileImgUrl() {
        return userInfo.getProfileImgUrl();
    }
}
