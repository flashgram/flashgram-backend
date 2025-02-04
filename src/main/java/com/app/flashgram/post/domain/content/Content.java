package com.app.flashgram.post.domain.content;

/**
 * 컨텐츠의 공통 속성과 유효성 검사를 정의하는 추상 클래스
 * 모든 컨텐츠는 이 클래스를 상속하여 구현
 */
public abstract class Content {

    String contentText;

    /**
     * 컨텐츠 객체를 생성
     * 생성 시 하위 클래스에서 정의한 유효성 검사
     *
     * @param contentText 컨텐츠 텍스트 내용
     */
    public Content(String contentText) {
        checkText(contentText);

        this.contentText = contentText;
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
