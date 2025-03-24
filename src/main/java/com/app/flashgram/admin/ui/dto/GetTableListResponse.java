package com.app.flashgram.admin.ui.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTableListResponse<T> {

    private int totalCount;
    private List<T> tableData;
}
