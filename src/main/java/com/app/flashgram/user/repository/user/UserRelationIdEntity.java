package com.app.flashgram.user.repository.user;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRelationIdEntity {

    public Long followerUserId;
    public Long followingUserId;
}
