package com.app.flashgram.notification.repository;

import com.app.flashgram.notification.repository.entity.FcmTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {

}
