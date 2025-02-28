package com.app.flashgram.acceptance.utils;

import static com.app.flashgram.acceptance.steps.UserAcceptanceSteps.followUser;

import com.app.flashgram.acceptance.steps.SignUpAcceptanceSteps;
import com.app.flashgram.auth.appliction.dto.CreateUserAuthRequestDto;
import com.app.flashgram.auth.appliction.dto.SendEmailRequestDto;
import com.app.flashgram.auth.repository.FakeEmailSendRepositoryImpl;
import com.app.flashgram.fake.FakeObjectFactory;
import com.app.flashgram.user.application.dto.FollowUserRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    private final FakeEmailSendRepositoryImpl fakeEmailSendRepository = (FakeEmailSendRepositoryImpl) FakeObjectFactory.getEmailSendRepository();

    public void loadData() {
        for (int i = 1; i < 4; i++) {
            createUser("user" + i + "@test.com");
        }

        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
        System.out.println("Data loaded successfully.");
    }

    public String getEmailToken(String email) {
        try {
            String token = fakeEmailSendRepository.getTokenForEmail(email);
            if (token != null) {
                return token;
            }

            return entityManager.createNativeQuery("SELECT token FROM fg_email_verification WHERE email = ?", String.class)
                                .setParameter(1, email)
                                .getSingleResult()
                                .toString();
        } catch (Exception e) {
            System.err.println("Error getting email token: " + e.getMessage());
            return null;
        }
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
        SignUpAcceptanceSteps.requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        SignUpAcceptanceSteps.requestVerifyEmail(email, token);
        SignUpAcceptanceSteps.registerUser(new CreateUserAuthRequestDto(email, "password", "USER", "name", ""));
    }
}