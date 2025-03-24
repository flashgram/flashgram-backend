package com.app.flashgram.post.common;

import java.time.LocalDateTime;

/**
 * 작성/수정 시간 정보를 표현하는 값 객체
 * 수정 여부와 최종 수정 시간을 관리
 */
public class DatetimeInfo {

    private Boolean isEdited;
    private LocalDateTime dateTime;

    /**
     * 새로운 시간 정보 객체를 생성
     * 생성 시점의 시간이 초기값으로 설정, 수정되지 않은 상태로 시작
     */
    public DatetimeInfo() {
        this.isEdited = false;
        this.dateTime = LocalDateTime.now();
    }

    /**
     * 컨텐츠가 수정되었을 때 호출, 수정 시간 정보를 업데이트
     * 수정 여부를 true로 변경, 수정 시간을 현재 시간으로 설정
     */
    public void updateEditDatetime() {
        this.isEdited = true;
        this.dateTime = LocalDateTime.now();
    }

    public Boolean isEdited() {
        return isEdited;
    }

    public LocalDateTime getDatetime() {
        return dateTime;
    }
}
