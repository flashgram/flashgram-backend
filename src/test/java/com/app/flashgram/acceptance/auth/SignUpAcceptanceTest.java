package com.app.flashgram.acceptance.auth;

import static com.app.flashgram.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.app.flashgram.acceptance.utils.AcceptanceTestTemplate;
import com.app.flashgram.auth.appliction.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignUpAcceptanceTest extends AcceptanceTestTemplate {

    private final String email = "email@email.com";

    @BeforeEach
    void setUp() {
        this.cleanUp();
    }

    @Test
    void givenEmail_whenSendEmail_thenVerificationTokenSave() {
        //given
        SendEmailRequestDto dto = new SendEmailRequestDto(email);

        //when
        Integer code = requestSendEmail(dto);

        //then
        String token = this.getEmailToken(email);

        assertNotNull(token);
        assertEquals(0, code);
    }

    @Test
    void givenInvalidEmail_whenEmailSend_thenVerificationTokenNotSaved() {
        //given
        SendEmailRequestDto dto = new SendEmailRequestDto("asd");

        //when
        Integer code = requestSendEmail(dto);

        //then
        assertEquals(400, code);
    }
}
