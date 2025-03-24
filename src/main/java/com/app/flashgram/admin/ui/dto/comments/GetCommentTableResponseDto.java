package com.app.flashgram.admin.ui.dto.comments;

import com.app.flashgram.common.utils.TimeCalculator;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentTableResponseDto {
    @Getter
    private Long commentId;
    @Getter
    private Long userId;
    @Getter
    private String userName;
    @Getter
    private Long postId;
    private String content;
    private LocalDateTime regAt;
    private LocalDateTime updAt;

    public String getContent() {
        if (content.length() > 10) {
            return content.substring(0, 10) + "...";
        }
        return content;
    }

    public String getRegAt() {
        return TimeCalculator.getFormattedDate(regAt);
    }

    public String getUpdAt() {
        return TimeCalculator.getFormattedDate(updAt);
    }
}