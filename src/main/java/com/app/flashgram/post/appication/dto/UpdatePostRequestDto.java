package com.app.flashgram.post.appication.dto;

import com.app.flashgram.post.domain.content.PostPublicationState;

public record UpdatePostRequestDto(Long id, Long userId, String content, PostPublicationState state) {

}
