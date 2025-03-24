package com.app.flashgram.post.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 게시물 컨텐츠에 대한 응답 DTO (댓글 수 포함)
 * 게시물 정보에 댓글 수를 추가한 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GetPostContentResponseDto extends GetContentResponseDto {

    private Integer commentCount;
}
