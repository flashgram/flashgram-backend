package com.app.flashgram.user.repository.user;

import com.app.flashgram.common.domain.PositiveIntegerCounter;
import com.app.flashgram.common.repository.entity.TimeBaseEntity;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.domain.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fg_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String profileImageUrl;
    private Integer followerCount;
    private Integer followingCount;

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImgUrl();
        this.followerCount = user.followerCount();
        this.followingCount = user.followingCount();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .userInfo(new UserInfo(name, profileImageUrl))
                .followerCounter(new PositiveIntegerCounter(followerCount))
                .followingCount(new PositiveIntegerCounter(followingCount))
                .build();
    }
}
