package com.app.flashgram.post.domain.common;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.app.flashgram.post.common.DatetimeInfo;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DatetimeInfoTest {

    @Test
    void givenCreated_whenUpdated_thenTimeAndStateArsUpdated() {
        //given
        DatetimeInfo dateTimeInfo = new DatetimeInfo();
        LocalDateTime localDateTime = dateTimeInfo.getDatetime();

        //when
        dateTimeInfo.updateEditDatetime();

        //then
        assertTrue(dateTimeInfo.isEdited());
        assertNotEquals(localDateTime, dateTimeInfo.getDatetime());
    }
}
