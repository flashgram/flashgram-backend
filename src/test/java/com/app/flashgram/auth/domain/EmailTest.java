package com.app.flashgram.auth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    void givenEmailIsEmpty_whenCreate_thenThrowError(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.createEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid/@coco", "naver.com", "coco#@naver", "안녕@안녕.com"})
    void givenInvalidEmail_whenCreate_thenThrowError(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.createEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid@coco", "coco@naver.com", "coco@naver", "test@test.com"})
    void givenInvalidEmail_whenCreate_thenReturnEmail(String email) {
        //give


        //when
        Email email1Value = Email.createEmail(email);

        //then
        assertEquals(email, email1Value.getEmailText());
    }

}
