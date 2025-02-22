package com.app.flashgram.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 시간 계산 관련 유틸리티 클래스
 */
public class TimeCalculator {

    /**
     * 날짜와 시간을 포맷팅하기 위한 기본 형식
     * 형식: "yyyy-MM-dd HH:mm:ss" (예: 2024-02-22 14:30:00)
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    /**
     * LocalDateTime 객체를 지정된 형식의 문자열로 변환
     *
     * @param dateTime 변환할 LocalDateTime 객체
     * @return 포맷팅된 날짜/시간 문자열, null이나 유효하지 않은 입력의 경우 빈 문자열 반환
     * 반환 형식: "yyyy-MM-dd HH:mm:ss"
     */
    public static String getFormattedDate(LocalDateTime dateTime) {
        if (dateTime == null) {

            return "";
        }

        return dateTime.format(FORMATTER);
    }
}
