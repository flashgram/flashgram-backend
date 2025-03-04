package com.app.flashgram.post.domain.content;

import com.app.flashgram.post.common.DatetimeInfo;

/**
 * 컨텐츠의 공통 속성과 유효성 검사를 정의하는 추상 클래스
 * 모든 컨텐츠는 이 클래스를 상속하여 구현
 */
public abstract class Content {

    protected String contentText;
    protected final DatetimeInfo datetimeInfo;

    /**
     * 컨텐츠 객체를 생성
     * 생성 시 하위 클래스에서 정의한 유효성 검사
     *
     * @param contentText 컨텐츠 텍스트 내용
     */
    public Content(String contentText) {
        checkText(contentText);

        this.contentText = contentText;
        this.datetimeInfo = new DatetimeInfo();
    }

    /**
     * 컨텐츠 내용을 업데이트하는 메서드
     * 업데이트 시 유효성 검사 수행 후 적용
     *
     * @param updateContent 변경할 컨텐츠 내용
     * @throws IllegalArgumentException 유효성 검사 실패 시 예외 발생
     */
    public void updateContent(String updateContent) {
        checkText(updateContent);

        this.contentText = updateContent;
        this.datetimeInfo.updateEditDatetime();
    }

    /**
     * 컨텐츠 텍스트의 유효성을 검사 추상 메서드
     * 각 하위 클래스는 자신만의 유효성 검사 규칙을 구현
     *
     * @param contentText 검사할 컨텐츠 텍스트
     */
    protected abstract void checkText(String contentText);

    public String getContentText() {
        return contentText;
    }
}
