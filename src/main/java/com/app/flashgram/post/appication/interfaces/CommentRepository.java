package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.ui.dto.GetCommentContentResponseDto;
import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);
    Comment findById(Long id);
    List<GetCommentContentResponseDto> findByPostId(Long postId, Long userId, Long lastCommentId);
}
