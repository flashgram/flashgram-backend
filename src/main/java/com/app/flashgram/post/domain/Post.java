package com.app.flashgram.post.domain;

import com.app.flashgram.common.domain.PositiveIntegerCounter;
import com.app.flashgram.post.domain.content.Content;
import com.app.flashgram.post.domain.content.PostContent;
import com.app.flashgram.post.domain.content.PostPublicationState;
import com.app.flashgram.user.domain.User;

/**
 * 게시글 도메인 객체
 */
public class Post {

    private final Long id;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;
    private PostPublicationState state;

    /**
     * 게시글 생성 정적 팩토리 메서드
     *
     * @param id 게시글 ID
     * @param author 게시글 작성자
     * @param content 게시글 내용
     * @param state 게시글 공개 상태
     * @return 생성된 게시글 객체
     */
    public static Post createPost(Long id, User author, String content, PostPublicationState state) {
        return new Post(id, author, new PostContent(content), state);
    }

    /**
     * 기본 공개 상태 게시글 생성을 위한 정적 팩토리 메서드
     *
     * @param id 게시글 ID
     * @param author 게시글 작성자
     * @param content 게시글 내용
     * @return 생성된 게시글 객체
     */
    public static Post createDefaultPost(Long id, User author, String content) {
        return new Post(id, author, new PostContent(content), PostPublicationState.PUBLIC);
    }

    /**
     * 기본 공개 상태 게시글 생성을 위한 생성자
     *
     * @param id 게시글 ID
     * @param author 게시글 작성자
     * @param content 게시글 내용
     */
    public Post(Long id, User author, PostContent content) {
        this(id, author, content, PostPublicationState.PUBLIC);
    }

    /**
     * 게시글 생성 기본 생성자
     *
     * @param id 게시글 ID
     * @param author 게시글 작성자
     * @param content 게시글 내용
     * @param state 게시글 공개 상태
     * @throws IllegalArgumentException 작성자가 null인 경우
     */
    public Post(Long id, User author, Content content, PostPublicationState state) {
        if (author == null) {
            throw new IllegalArgumentException("작성자는 null일 수 없습니다.");
        }

        this.id = id;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
        this.state = state;
    }

    /**
     * 게시글에 좋아요 추가
     * 작성자 본인은 자신의 게시글에 좋아요를 할 수 없음
     *
     * @param user 좋아요를 누르는 유저
     * @throws IllegalArgumentException 게시글 작성자가 자신의 게시글에 좋아요를 시도하는 경우
     */
    public void like(User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException("자신을 좋아요 할 수 없습니다.");
        }

        likeCount.increase();
    }

    /**
     * 게시글의 좋아요 취소
     */
    public void unlike() {
        this.likeCount.decrease();
    }

    /**
     * 게시글의 내용 수정
     * 게시글 작성자만 수정
     *
     * @param user           수정을 요청한 유저
     * @param updateContent 수정할 게시글 내용
     * @throws IllegalArgumentException 게시글 작성자가 아닌 유저가 수정을 시도하는 경우
     */
    public void updatePost(User user, String updateContent, PostPublicationState state) {
        if (!this.author.equals(user)) {
            throw new IllegalArgumentException("자신의 게시글만 수정할 수 있습니다.");
        }

        this.state = state;
        this.content.updateContent(updateContent);
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }

    public String getContent() {
        return content.getContentText();
    }
}
