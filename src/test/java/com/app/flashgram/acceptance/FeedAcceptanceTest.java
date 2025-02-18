package com.app.flashgram.acceptance;

import static com.app.flashgram.acceptance.steps.FeedAcceptanceSteps.requestCreatePost;
import static com.app.flashgram.acceptance.steps.FeedAcceptanceSteps.requestFeed;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.app.flashgram.acceptance.utils.AcceptanceTestTemplate;
import com.app.flashgram.post.appication.dto.CreatePostRequestDto;
import com.app.flashgram.post.domain.content.PostPublicationState;
import com.app.flashgram.post.ui.dto.GetPostContentResponseDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    @BeforeEach
    void setUp() {
        super.init();
    }

    /**
     * User 2 create Post 1
     * User 1 Get Post 1 From Feed
     */
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetFromFeed() {
        //given
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "user1 can get this post", PostPublicationState.PUBLIC);
        Long createPostId = requestCreatePost(dto);

        //when
        List<GetPostContentResponseDto> result = requestFeed(1L);

        //then
        assertEquals(1, result.size());
        assertEquals(createPostId, result.get(0).getId());
    }
}
