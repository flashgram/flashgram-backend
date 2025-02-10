package com.app.flashgram.post.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.app.flashgram.fake.FakeObjectFactory;
import com.app.flashgram.post.appication.PostService;
import com.app.flashgram.post.appication.dto.CreatePostRequestDto;
import com.app.flashgram.post.appication.dto.LikeRequestDto;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.content.PostPublicationState;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.application.dto.CreateUserRequestDto;
import com.app.flashgram.user.domain.User;
import org.junit.jupiter.api.Test;

class PostServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();
    private final PostService postService = FakeObjectFactory.getPostService();

    private final User user = userService.createUser(new CreateUserRequestDto("user1", null));
    private final User otherUser = userService.createUser(new CreateUserRequestDto("user1", null));

    private final CreatePostRequestDto dto = new CreatePostRequestDto(user.getId(), "this is test content",
            PostPublicationState.PUBLIC);

    private final Post post = postService.createPost(dto);

    @Test
    void givenPostRequestDto_whenCreate_thenReturnPost() {
        //when
        Post savePost = postService.createPost(dto);

        //then
        Post post = postService.getPost(savePost.getId());
        assertEquals(savePost, post);
    }

    @Test
    void givenCreatedPost_whenUpdate_thenReturnUpdatePost() {
        //given
        Post savePost = postService.createPost(dto);

        //when
        Post updatePost = postService.updatePost(savePost.getId(), dto);

        //then
        assertEquals(savePost.getId(), updatePost.getId());
        assertEquals(savePost.getAuthor(), updatePost.getAuthor());
        assertEquals(savePost.getContent(), updatePost.getContent());
    }

    @Test
    void givenCreatedPost_whenLike_thenReturnPostWithLike() {
        //given
        Post savePost = postService.createPost(dto);

        //when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savePost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        //then
        assertEquals(1, savePost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenLikeTwice_thenReturnPostWitLike() {
        //given
        Post savePost = postService.createPost(dto);

        //when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savePost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        //then
        assertEquals(1, savePost.getLikeCount());
    }

    @Test
    void givenCreatedPostLiked_whenUnliked_thenReturnPostWithUnlike() {
        //given
        Post savePost = postService.createPost(dto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savePost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        //when
        postService.unlikePost(likeRequestDto);

        //then
        assertEquals(0, savePost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenUnliked_thenReturnPostWithoutLike() {
        //given
        Post savePost = postService.createPost(dto);

        //when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savePost.getId(), otherUser.getId());
        postService.unlikePost(likeRequestDto);

        //then
        assertEquals(0, savePost.getLikeCount());
    }
}
