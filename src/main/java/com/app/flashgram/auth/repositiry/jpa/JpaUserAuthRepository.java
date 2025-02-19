package com.app.flashgram.auth.repositiry.jpa;

import com.app.flashgram.auth.repositiry.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, String> {

}
