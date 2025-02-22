package com.app.flashgram.admin.ui.query;

import com.app.flashgram.admin.ui.dto.GetTableListResponse;
import com.app.flashgram.admin.ui.dto.Users.GetUserTableRequestDto;
import com.app.flashgram.admin.ui.dto.Users.GetUserTableResponseDto;

public interface AdminTableQueryRepository {

    GetTableListResponse<GetUserTableResponseDto> getUserTableData(
            GetUserTableRequestDto dto);
}
