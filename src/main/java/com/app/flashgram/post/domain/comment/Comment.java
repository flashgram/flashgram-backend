package com.app.flashgram.post.domain.comment;

import com.app.flashgram.common.domain.PositiveIntegerCounter;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.content.CommentContent;
import com.app.flashgram.user.domain.User;

/**
 * 댓글 도메인 객체
 */
public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final CommentContent content;
    private final PositiveIntegerCounter likeCount;

    /**
     * 게시글 도메인 객체를 생성
     *
     * @param id      댓글 식별자
     * @param post    게시글
     * @param author  댓글 작성자
     * @param content 댓글 내용
     * @throws IllegalArgumentException 작성자, 게시글, 댓글 내용이 null인 경우
     */
    public Comment(Long id, Post post, User author, CommentContent content) {
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
        this.likeCount = new PositiveIntegerCounter();
    }

    /**
     * 댓글에 좋아요 추가
     * 작성자 본인은 자신의 댓글에 좋아요를 할 수 없음
     *
     * @param user 좋아요를 누르는 유저
     * @throws IllegalArgumentException 댓글 작성자가 자신의 댓글에 좋아요를 시도하는 경우
     */
    public void like(User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        likeCount.increase();
    }

    /**
     * 댓글의 좋아요 취소
     */
    public void unlike() {
        this.likeCount.decrease();
    }

    /**
     * 댓글의 내용 수정
     * 댓글 작성자만 수정
     *
     * @param user           수정을 요청한 유저
     * @param updateContent 수정할 댓글 내용
     * @throws IllegalArgumentException 댓글 작성자가 아닌 유저가 수정을 시도하는 경우
     */
    public void updateComment(User user, String updateContent) {
        if (!this.author.equals(user)) {
            throw new IllegalArgumentException("자신의 댓글만 수정할 수 있습니다.");
        }

        this.content.updateContent(updateContent);
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }

    public String getContent() {
        return content.getContentText();
    }
}
