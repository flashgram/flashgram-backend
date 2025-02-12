package com.app.flashgram.user.repository.jpa;

import com.app.flashgram.user.repository.entity.UserRelationEntity;
import com.app.flashgram.user.repository.entity.UserRelationIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {

}
