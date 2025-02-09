package com.app.flashgram.post.appication.dto;

public record UpdateCommentRequestDto(Long commentId, Long userId, String content) {

}
