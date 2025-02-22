package com.app.flashgram.admin.ui;

import com.app.flashgram.admin.ui.dto.GetTableListResponse;
import com.app.flashgram.admin.ui.dto.Users.GetDailyUserResponseDto;
import com.app.flashgram.admin.ui.dto.Users.GetUserTableRequestDto;
import com.app.flashgram.admin.ui.dto.Users.GetUserTableResponseDto;
import com.app.flashgram.admin.ui.query.AdminTableQueryRepository;
import com.app.flashgram.admin.ui.query.UserStatsQueryRepository;
import com.app.flashgram.common.ui.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 기능을 제공 컨트롤러
 * 관리자 대시보드의 통계 정보와 유저 테이블 데이터를 조회하는 엔드포인트 제공
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;
    private final AdminTableQueryRepository adminTableQueryRepository;

    /**
     * 관리자 대시보드에 표시할 통계 정보 조회
     * 최근 7일간의 일별 유저 등록 통계 제공
     *
     * @return 일별 유저 등록 통계 목록
     */
    @GetMapping("/index")
    public Response<List<GetDailyUserResponseDto>> index() {
        List<GetDailyUserResponseDto> result = userStatsQueryRepository.getDailyResisterUserStats(7);

        return Response.ok(result);
    }

    /**
     * 관리자 페이지의 유저 테이블 데이터를 조회합니다.
     * 페이징과 필터링이 적용된 유저 목록을 반환합니다.
     *
     * @param dto 유저 테이블 조회를 위한 요청 파라미터
     *            (페이지 번호, 페이지 크기, 검색 조건 등을 포함)
     * @return ResponseEntity<GetTableListResponse<GetUserTableResponseDto>>
     *         - 성공 시: 페이징된 유저 목록과 전체 데이터 수가 포함된 응답
     *         - 실패 시: 적절한 HTTP 상태 코드와 에러 메시지
     */
    @GetMapping("/users")
    public ResponseEntity<GetTableListResponse<GetUserTableResponseDto>> users(GetUserTableRequestDto dto) {
        GetTableListResponse<GetUserTableResponseDto> result = adminTableQueryRepository.getUserTableData(dto);

        return ResponseEntity.ok(result);
    }
}
