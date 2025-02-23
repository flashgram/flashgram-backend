package com.app.flashgram.admin.ui.dto.users;

import com.app.flashgram.common.utils.TimeCalculator;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserTableResponseDto {

    @Getter
    private Long id;
    @Getter
    private String email;
    @Getter
    private String name;
    @Getter
    private String role;
    private LocalDateTime regAt;
    private LocalDateTime updAt;
    private LocalDateTime lastLoginDt;

    public String getRegAt() {

        return TimeCalculator.getFormattedDate(regAt);
    }

    public String getUpdAt() {

        return TimeCalculator.getFormattedDate(updAt);
    }

    public String getLastLoginAt() {

        return TimeCalculator.getFormattedDate(lastLoginDt);
    }
}
