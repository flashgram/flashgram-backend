package com.app.flashgram.post.domain.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CommentContentTest {

    @Test
    void givenContentLengthIsOk_whenCreatedCommentContent_thenReturnTextContent() {
        //given
        String contentText = "This is a test content";

        //when
        CommentContent content = new CommentContent(contentText);

        //then
        assertEquals(contentText, content.contentText);
    }

    @Test
    void givenContentLengthIsOver_whenCreatedCommentContent_thenThrowError() {
        //given
        String content = "a".repeat(301);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @ValueSource(strings = {"갉", "닭", "슭", "칡"})
    void givenContentLengthIsOverAndKorean_whenCreatedCommentContent_thenThrowError(String koreanContent) {
        //given
        String content = koreanContent.repeat(301);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentLengthIsEmptyAndNull_whenCreatedCommentContent_thenThrowError(String content) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @Test
    void givenContentLengthIsUnder_whenCreatedCommentContent_thenThrowError() {
        //given
        String content = "a".repeat(4);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }
}
