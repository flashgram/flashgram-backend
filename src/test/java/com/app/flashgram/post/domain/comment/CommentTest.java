package com.app.flashgram.post.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.content.CommentContent;
import com.app.flashgram.post.domain.content.PostContent;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

class CommentTest {

    private final UserInfo info = new UserInfo("name1", "url");
    private final User user = new User(1L, info);
    private final User otherUser = new User(2L, info);

    private final Post post = new Post(1L, user, new PostContent("content"));
    private final Comment comment = new Comment(1L, post, user, new CommentContent("content"));

    @Test
    void givenCommentCreated_whenLike_thenLikeCountShouldBe1() {
        //when
        comment.like(otherUser);

        //then
        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeBySelf_thenThrowError() {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> comment.like(user));
    }

    @Test
    void givenCommentCreatedAndLike_whenUnlike_thenLikeCountShouldBe0() {
        //given
        comment.like(otherUser);

        //when
        comment.unlike();

        //then
        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenCommentCreated_whenUnlike_thenLikeCountShouldBe0() {
        //when
        post.unlike();

        //then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenComment_whenUpdateContent_thenShouldBeUpdated() {
        //given
        String newCommentContent = "new content";

        //when
        comment.updateComment(user, newCommentContent);

        //then
    assertEquals(newCommentContent, comment.getContent());
    }

    @Test
    void givenCommentCreated_whenUpdatedOtherUserContent_thenThrowError() {
        //given
        String newCommentContent = "new content";

        //when, then
        assertThrows(IllegalArgumentException.class, () -> comment.updateComment(otherUser, newCommentContent));
    }
}
