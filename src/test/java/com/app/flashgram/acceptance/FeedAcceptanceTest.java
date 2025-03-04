package com.app.flashgram.acceptance;

import static com.app.flashgram.acceptance.steps.FeedAcceptanceSteps.requestCreatePost;
import static com.app.flashgram.acceptance.steps.FeedAcceptanceSteps.requestFeed;
import static com.app.flashgram.acceptance.steps.FeedAcceptanceSteps.requestFeedCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.app.flashgram.acceptance.utils.AcceptanceTestTemplate;
import com.app.flashgram.post.application.dto.CreatePostRequestDto;
import com.app.flashgram.post.domain.content.PostPublicationState;
import com.app.flashgram.post.ui.dto.GetPostContentResponseDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    private String token;

    @BeforeEach
    void setUp() {
        super.init();
        this.token = login("user1@test.com");
    }

    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetFromFeed() {
        //given
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "user1 can get this post", PostPublicationState.PUBLIC);
        Long createPostId = requestCreatePost(dto);
        System.out.println("Created Post ID: " + createPostId);

        //when
        List<GetPostContentResponseDto> result = requestFeed(token);

        //then
        assertEquals(1, result.size());
        assertEquals(createPostId, result.get(0).getId());
    }

    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeedWithInvalidToken_thenFollowerCanGetPostFromFeed() {
        //when
        Integer code = requestFeedCode("abcd");

        //then
        assertEquals(400, code);
    }
}
