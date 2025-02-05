package com.app.flashgram.user.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UserInfoTest {

    @Test
    void givenNameAndProfileImg_whenCreated_thenTrowNothing() {
        //given
        String name = "name";
        String profileImg = "";

        //when
        //then
        assertDoesNotThrow(() -> new UserInfo(name, profileImg));
    }

    @Test
    void givenBlankNameAndProfileImg_whenCreated_thenTrowError() {
        //given
        String name = "";
        String profileImg = "profileImg";

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new UserInfo(name, profileImg));
    }
}
