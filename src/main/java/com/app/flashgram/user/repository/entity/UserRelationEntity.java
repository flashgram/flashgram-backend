package com.app.flashgram.user.repository.entity;

import com.app.flashgram.common.repository.entity.TimeBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fg_user_relation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@IdClass(UserRelationIdEntity.class)
public class UserRelationEntity extends TimeBaseEntity {

    @Id
    public Long followingUserId;

    @Id
    public Long followerUserId;
}
