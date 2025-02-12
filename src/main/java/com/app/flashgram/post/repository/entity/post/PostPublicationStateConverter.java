package com.app.flashgram.post.repository.entity.post;

import com.app.flashgram.post.domain.content.PostPublicationState;
import jakarta.persistence.AttributeConverter;

public class PostPublicationStateConverter implements
        AttributeConverter<PostPublicationState, String> {

    @Override
    public String convertToDatabaseColumn(PostPublicationState postPublicationState) {
        return postPublicationState.name();
    }

    @Override
    public PostPublicationState convertToEntityAttribute(String s) {
        return PostPublicationState.valueOf(s);
    }
}
