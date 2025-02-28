package com.app.flashgram.auth.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PasswordTest {

    @Test
    void givePassword_whenMatchSamePassword_thenReturnTrue() {
        Password password = Password.creatEncryptPassword("password");

        assertTrue(password.matchPassword("password"));
    }

    @Test
    void givePassword_whenMatchDifferentPassword_thenReturnFalse() {
        Password password = Password.creatEncryptPassword("password1");

        assertFalse(password.matchPassword("password"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givePasswordIsNull_thenThrowError(String password) {
        assertThrows(IllegalArgumentException.class, () -> Password.creatEncryptPassword(password));
    }
}
