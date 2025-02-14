package com.app.flashgram.post.repository.post_queue;

import com.app.flashgram.post.ui.dto.GetPostContentResponseDto;
import java.util.List;

public interface UserPostQueueQueryRepository {

    List<GetPostContentResponseDto> getContentResponse(Long user_id, Long lastPostId);
}
