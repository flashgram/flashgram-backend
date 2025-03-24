package com.app.flashgram.post.application.dto;

public record CreateCommentRequestDto(Long postId, Long userId, String content) {

}
