package com.app.flashgram.admin.ui.dto.Users;

import com.app.flashgram.common.domain.Pageable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserTableRequestDto extends Pageable {

    private String name;
}
