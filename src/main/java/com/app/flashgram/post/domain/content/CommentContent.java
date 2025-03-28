package com.app.flashgram.post.domain.content;

/**
 * 댓글 내용을 관리하는 클래스
 * 댓글의 길이를 검증하는 로직 포함
 */
public class CommentContent extends Content {

    public static final int MIN_COMMENT_LENGTH = 5;
    public static final int MAX_COMMENT_LENGTH = 300;

    public CommentContent(String content) {
        super(content);
    }

    /**
     * 댓글 내용의 유효성 검사
     *
     * @param content 검증할 댓글 내용
     * @throws IllegalArgumentException 댓글이 비어 있거나 길이 제한을 초과할 경우 예외 발생
     */
    @Override
    public void checkText(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("댓글이 비어 있거나 null일 수 없습니다.");
        }

        if (content.length() < MIN_COMMENT_LENGTH || content.length() > MAX_COMMENT_LENGTH) {
            throw new IllegalArgumentException("댓글의 길이가 유효한 범위(최소 " + MIN_COMMENT_LENGTH + "자, 최대 " + MAX_COMMENT_LENGTH + "자) 내에 있지 않습니다.");
        }
    }

}
