package com.app.flashgram.acceptance.auth;

import static com.app.flashgram.acceptance.steps.LoginAcceptanceSteps.requestLoginGetCode;
import static com.app.flashgram.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.app.flashgram.acceptance.utils.AcceptanceTestTemplate;
import com.app.flashgram.auth.appliction.dto.LoginRequestDto;
import com.app.flashgram.auth.domain.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginAcceptanceTest extends AcceptanceTestTemplate {

    private final String email = "test@test.com";
    private final TokenProvider tokenProvider = new TokenProvider("testteststestteststestteststestteststestteststestteststestteststestteststestteststesttests");

    @BeforeEach
    void setUp() {
        this.cleanUp();
        this.createUser(email);
    }

    @Test
    void givenEmailAndPassword_whenLogin_thenReturnToken() {
        //given
        LoginRequestDto dto = new LoginRequestDto(email, "password");

        //when
        String token = requestLoginGetToken(dto);

        //then
        assertNotNull(token);
    }

    @Test
    void givenEmailWrongPassword_whenLogin_thenReturnCodeNotZero() {
        //given
        LoginRequestDto dto = new LoginRequestDto(email, "wrongPassword");

        //when
        Integer code = requestLoginGetCode(dto);

        //then
        assertEquals(400, code);
    }
}
