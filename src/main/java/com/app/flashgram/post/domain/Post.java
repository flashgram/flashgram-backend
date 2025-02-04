package com.app.flashgram.post.domain;

import com.app.flashgram.post.domain.content.PostContent;
import com.app.flashgram.user.domain.User;

/**
 * 게시글 도메인 객체
 */
public class Post {

    private final Long id;
    private final User author;
    private final PostContent content;

    /**
     * 게시글 도메인 객체를 생성
     *
     * @param id      게시글 식별자
     * @param author  게시글 작성자
     * @param content 게시글 내용
     * @throws IllegalArgumentException 작성자가 null인 경우
     */
    public Post(Long id, User author, PostContent content) {
        if (author == null) {
            throw new IllegalArgumentException("작성자는 null일 수 없습니다.");
        }

        this.id = id;
        this.author = author;
        this.content = content;
    }
}
