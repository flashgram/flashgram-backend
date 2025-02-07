package com.app.flashgram.post.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.app.flashgram.post.domain.content.PostContent;
import com.app.flashgram.post.domain.content.PostPublicationState;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

class PostTest {

    private final UserInfo info = new UserInfo("name1", "url");
    private final User user = new User(1L, info);
    private final User otherUser = new User(2L, info);

    private final Post post = new Post(1L, user, new PostContent("content"));

    @Test
    void givenPostCreated_whenLike_thenLikeCountShouldBe1() {
        //when
        post.like(otherUser);

        //then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeBySelf_thenThrowError() {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> post.like(user));
    }

    @Test
    void givenPostCreatedAndLike_whenUnlike_thenLikeCountShouldBe0() {
        //given
        post.like(otherUser);

        //when
        post.unlike();

        //then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenUnlike_thenLikeCountShouldBe0() {
        //when
        post.unlike();

        //then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenUpdatedContent_thenContentShouldBeUpdated() {
        //given
        String newPostContent = "new content";

        //when
        post.updatePost(user, newPostContent, PostPublicationState.PUBLIC);

        //then
        assertEquals(newPostContent, post.getContent());;
    }

    @Test
    void givenPostCreated_whenUpdatedOtherUserContent_thenThrowError() {
        //given
        String newPostContent = "new content";

        //when, then
        assertThrows(IllegalArgumentException.class, () -> post.updatePost(otherUser, newPostContent, PostPublicationState.PUBLIC));
    }
}
