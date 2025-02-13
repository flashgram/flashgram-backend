package com.app.flashgram.post.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.app.flashgram.post.appication.dto.LikeRequestDto;
import com.app.flashgram.post.appication.dto.UpdatePostRequestDto;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.content.PostPublicationState;
import org.junit.jupiter.api.Test;

class PostServiceTest extends PostApplicationTestTemplate {

    @Test
    void givenPostRequestDto_whenCreate_thenReturnPost() {
        //when
        Post savePost = postService.createPost(postRequestDto);

        //then
        Post post = postService.getPost(savePost.getId());
        assertEquals(savePost, post);
    }

    @Test
    void givenCreatedPost_whenUpdate_thenReturnUpdatePost() {
        //given
        Post savedPost = postService.createPost(postRequestDto);

        //when
        UpdatePostRequestDto updatePostDto = new UpdatePostRequestDto(user.getId(), "updated-content", PostPublicationState.PRIVATE);
        Post updatePost = postService.updatePost(savedPost.getId(), updatePostDto);

        //then
        assertEquals(savedPost.getId(), updatePost.getId());
        assertEquals(savedPost.getAuthor(), updatePost.getAuthor());
        assertEquals(savedPost.getContent(), updatePost.getContent());
    }

    @Test
    void givenCreatedPost_whenLike_thenReturnPostWithLike() {
        //given
        Post savePost = postService.createPost(postRequestDto);

        //when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savePost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        //then
        assertEquals(1, savePost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenLikeTwice_thenReturnPostWitLike() {
        //given
        Post savePost = postService.createPost(postRequestDto);

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
        Post savePost = postService.createPost(postRequestDto);
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
        Post savePost = postService.createPost(postRequestDto);

        //when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savePost.getId(), otherUser.getId());
        postService.unlikePost(likeRequestDto);

        //then
        assertEquals(0, savePost.getLikeCount());
    }
}
