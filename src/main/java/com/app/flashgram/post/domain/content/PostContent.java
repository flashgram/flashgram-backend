package com.app.flashgram.post.domain.content;

/**
 * 게시글의 내용 값 객체
 * 게시글 내용은 최소 5자, 최대 1000자
 */
public class PostContent extends Content {

    private static final int MIN_POST_LENGTH = 5;
    private static final int MAX_POST_LENGTH = 1000;

    /**
     * 게시글 내용 객체를 생성
     *
     * @param content 게시글 내용
     */
    public PostContent(String content) {
        super(content);
    }

    /**
     * 게시글 내용의 유효성을 검사
     * 내용은 null이나 빈 문자열일 수 없으며, 5자 이상 1000자 이하
     *
     * @param contentText 검사할 게시글 내용
     * @throws IllegalArgumentException 유효성 검사를 통과하지 못한 경우
     */
    @Override
    protected void checkText(String contentText) {
        if (contentText == null ||contentText.isEmpty()) {
            throw new IllegalArgumentException("내용은 비어 있거나 null일 수 없습니다.");
        }

        if (contentText.length() < MIN_POST_LENGTH || contentText.length() > MAX_POST_LENGTH) {
            throw new IllegalArgumentException("내용의 길이가 유효한 범위(최소 " + MIN_POST_LENGTH + "자, 최대 " + MAX_POST_LENGTH + "자) 내에 있지 않습니다.");
        }
    }
}
