package com.app.flashgram.post.application.interfaces;

import com.app.flashgram.post.ui.dto.GetCommentContentResponseDto;
import java.util.List;

public interface CommentQueryRepository {

    List<GetCommentContentResponseDto> findByPostId(Long postId, Long userId, Long lastCommentId);
}
