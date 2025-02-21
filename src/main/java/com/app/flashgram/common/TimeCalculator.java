package com.app.flashgram.common;

import java.time.LocalDate;

/**
 * 시간 계산 관련 유틸리티 클래스
 */
public class TimeCalculator {

    private TimeCalculator() {

    }

    /**
     * 현재 날짜로부터 지정된 일수만큼 이전의 날짜 계산
     *
     * @param daysAgo 이전 일수
     * @return 계산된 이전 날짜
     */
    public static LocalDate getDateDaysAgo(int daysAgo) {
        LocalDate currentDate = LocalDate.now();

        return currentDate.minusDays(daysAgo);
    }
}
