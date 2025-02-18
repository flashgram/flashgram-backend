package com.app.flashgram.post.ui.dto;

import com.app.flashgram.post.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GetCommentContentResponseDto extends GetContentResponseDto {

    private Long postId;

    public static GetCommentContentResponseDto from(Comment comment, boolean isLikedByMe) {

        return GetCommentContentResponseDto.builder()
                                           .id(comment.getId())
                                           .content(comment.getContent())
                                           .userId(comment.getAuthor().getId())
                                           .userName(comment.getAuthor().getName())
                                           .userProfileImgUrl(comment.getAuthor().getProfileImgUrl())
                                           .likeCount(comment.getLikeCount())
                                           .isLikedByMe(isLikedByMe)
                                           .postId(comment.getPost().getId())
                                           .build();
    }
}
