package com.app.flashgram.post.domain.comment;

import com.app.flashgram.post.domain.Post;
import com.app.flashgram.user.domain.User;

/**
 * 댓글 도메인 객체
 */
public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final String content;

    /**
     * 게시글 도메인 객체를 생성
     *
     * @param id      댓글 식별자
     * @param post    게시글
     * @param author  댓글 작성자
     * @param content 댓글 내용
     * @throws IllegalArgumentException 작성자, 게시글, 댓글 내용이 null인 경우
     */
    public Comment(Long id, Post post, User author, String content) {
        if (author == null) {
            throw new IllegalArgumentException("작성자는 null일 수 없습니다.");
        }

        if (post == null) {
            throw new IllegalArgumentException("게시글은 null일 수 없습니다.");
        }

        if (content == null) {
            throw new IllegalArgumentException("댓글 내용은 null일 수 없습니다.");
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
    }
}
