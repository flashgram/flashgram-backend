package com.app.flashgram.post.appication.dto;

public record CreateCommentRequestDto(Long postId, Long userId, String content) {

}
