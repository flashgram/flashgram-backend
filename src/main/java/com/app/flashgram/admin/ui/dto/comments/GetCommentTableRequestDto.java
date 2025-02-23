package com.app.flashgram.admin.ui.dto.comments;

import com.app.flashgram.common.domain.Pageable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentTableRequestDto extends Pageable {

    private Long commentId;
    private Long postId;
}
