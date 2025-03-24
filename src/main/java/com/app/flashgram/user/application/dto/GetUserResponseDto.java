package com.app.flashgram.user.application.dto;

import com.app.flashgram.user.domain.User;

public record GetUserResponseDto(Long id, String name, String profileImageUrl, Integer followingCount, Integer followerCount) {

    public GetUserResponseDto(User user) {
        this(user.getId(), user.getName(), user.getProfileImgUrl(), user.followingCount(),
                user.followerCount());
    }
}
