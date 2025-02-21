package com.app.flashgram.admin.ui.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDailyUserResponseDto {

    private LocalDate regDate;
    private Long count;
}
