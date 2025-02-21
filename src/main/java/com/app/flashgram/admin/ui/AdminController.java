package com.app.flashgram.admin.ui;

import com.app.flashgram.admin.ui.dto.GetDailyUserResponseDto;
import com.app.flashgram.admin.ui.query.UserStatsQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 기능을 제공 컨트롤러
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;

    /**
     * 관리자 대시보드에 표시할 통계 정보 조회
     * 최근 7일간의 일별 유저 등록 통계 제공
     *
     * @return 일별 유저 등록 통계 목록
     */
    @GetMapping("/index")
    public ResponseEntity<List<GetDailyUserResponseDto>> index() {
        List<GetDailyUserResponseDto> result = userStatsQueryRepository.getDailyResisterUserStats(7);

        return ResponseEntity.ok(result);
    }
}
