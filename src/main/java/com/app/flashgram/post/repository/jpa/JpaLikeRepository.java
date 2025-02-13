package com.app.flashgram.post.repository.jpa;

import com.app.flashgram.post.repository.entity.like.LikeEntity;
import com.app.flashgram.post.repository.entity.like.LikeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeIdEntity> {

}
