package com.app.flashgram.admin.ui.query;

import com.app.flashgram.admin.ui.dto.GetTableListResponse;
import com.app.flashgram.admin.ui.dto.comments.GetCommentTableRequestDto;
import com.app.flashgram.admin.ui.dto.comments.GetCommentTableResponseDto;
import com.app.flashgram.admin.ui.dto.posts.GetPostTableRequestDto;
import com.app.flashgram.admin.ui.dto.posts.GetPostTableResponseDto;
import com.app.flashgram.admin.ui.dto.users.GetUserTableRequestDto;
import com.app.flashgram.admin.ui.dto.users.GetUserTableResponseDto;

public interface AdminTableQueryRepository {

    GetTableListResponse<GetUserTableResponseDto> getUserTableData(
            GetUserTableRequestDto dto);

    GetTableListResponse<GetPostTableResponseDto> getPostTableData(GetPostTableRequestDto dto);

    GetTableListResponse<GetCommentTableResponseDto> getCommentTableData(GetCommentTableRequestDto dto);
}
