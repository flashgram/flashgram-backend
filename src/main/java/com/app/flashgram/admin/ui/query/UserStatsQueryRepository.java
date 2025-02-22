package com.app.flashgram.admin.ui.query;

import com.app.flashgram.admin.ui.dto.Users.GetDailyUserResponseDto;
import java.util.List;

/**
 * 유저 통계 정보를 조회하는 레포지토리 인터페이스
 */
public interface UserStatsQueryRepository {

    /**
     * 지정된 일수 이전부터 현재까지의 일별 유저 등록 통계 조회
     *
     * @param beforeDays 조회할 이전 일수
     * @return 일별 유저 등록 통계 목록
     */
    List<GetDailyUserResponseDto> getDailyResisterUserStats(int beforeDays);
}
