package com.app.flashgram.post.ui.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 게시물 컨텐츠에 대한 응답 DTO
 * 게시물의 기본적인 정보(내용, 작성자, 좋아요 수 등)를 포함
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GetContentResponseDto {

    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String userProfileImgUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likeCount;
    private boolean isLikedByMe;
}
