package com.app.flashgram.post.domain.content;

public class CommentContent extends Content {

    public static final int MIN_COMMENT_LENGTH = 5;
    public static final int MAX_COMMENT_LENGTH = 300;

    public CommentContent(String content) {
        super(content);
    }

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
