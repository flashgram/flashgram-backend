package com.app.flashgram.acceptance.utils;

import static com.app.flashgram.acceptance.steps.SignUpAcceptanceSteps.registerUser;
import static com.app.flashgram.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static com.app.flashgram.acceptance.steps.SignUpAcceptanceSteps.requestVerifyEmail;
import static com.app.flashgram.acceptance.steps.UserAcceptanceSteps.followUser;

import com.app.flashgram.auth.appliction.dto.CreateUserAuthRequestDto;
import com.app.flashgram.auth.appliction.dto.SendEmailRequestDto;
import com.app.flashgram.user.application.dto.FollowUserRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    public void loadData() {

        for (int i = 1; i <4; i++) {
            createUser("user" + i + "@test.com");
        }

        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
        System.out.println("Data loaded successfully.");
    }

    public String getEmailToken(String email) {
        return entityManager.createNativeQuery("SELECT token FROM fg_email_verification WHERE email = ?", String.class)
                .setParameter(1, email)
                .getSingleResult()
                .toString();
    }

    public boolean isEmailVerified(String email) {
        return entityManager.createQuery("SELECT isVerified FROM EmailVerificationEntity WHERE email = :email", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Long getUserId(String email) {
        return entityManager.createQuery("SELECT userId FROM UserAuthEntity WHERE email = :email", Long.class)
                            .setParameter("email", email)
                            .getSingleResult();
    }

    public void createUser(String email) {
        requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        requestVerifyEmail(email, token);
        registerUser(new CreateUserAuthRequestDto(email, "password", "USER", "name", ""));
    }
}
