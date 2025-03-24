package com.app.flashgram.acceptance.utils;


import com.app.flashgram.auth.appliction.interfaces.EmailSendRepository;
import com.app.flashgram.auth.repository.FakeEmailSendRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    @Primary
    public EmailSendRepository emailSendRepository() {
        return new FakeEmailSendRepositoryImpl();
    }
}