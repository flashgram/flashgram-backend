package com.app.flashgram.post.application.dto;

import com.app.flashgram.post.domain.content.PostPublicationState;

public record CreatePostRequestDto(Long userId, String content, PostPublicationState state) {

}
