package com.app.flashgram.admin.ui;

import com.app.flashgram.admin.ui.dto.GetTableListResponse;
import com.app.flashgram.admin.ui.dto.comments.GetCommentTableRequestDto;
import com.app.flashgram.admin.ui.dto.comments.GetCommentTableResponseDto;
import com.app.flashgram.admin.ui.dto.posts.GetPostTableRequestDto;
import com.app.flashgram.admin.ui.dto.posts.GetPostTableResponseDto;
import com.app.flashgram.admin.ui.dto.users.GetDailyUserResponseDto;
import com.app.flashgram.admin.ui.dto.users.GetUserTableRequestDto;
import com.app.flashgram.admin.ui.dto.users.GetUserTableResponseDto;
import com.app.flashgram.admin.ui.query.AdminTableQueryRepository;
import com.app.flashgram.admin.ui.query.UserStatsQueryRepository;
import com.app.flashgram.common.ui.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 기능을 제공하는 컨트롤러
 * 관리자 대시보드의 통계 정보와 유저, 게시글, 댓글 데이터를 조회하는
 * 엔드포인트 제공
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
     * 관리자 페이지의 유저 테이블 데이터 조회
     * 페이징과 필터링이 적용된 유저 목록 반환
     *
     * @param dto 유저 테이블 조회를 위한 요청 파라미터
     *            (페이지 번호, 페이지 크기, 검색 조건 등을 포함)
     * @return ResponseEntity<GetTableListResponse<GetUserTableResponseDto>>
     *         - 성공 시: 페이징된 유저 목록과 전체 데이터 수가 포함된 응답
     *         - 실패 시: 적절한 HTTP 상태 코드와 에러 메시지
     */
    @GetMapping("/users")
    public Response<GetTableListResponse<GetUserTableResponseDto>> users(GetUserTableRequestDto dto) {
        GetTableListResponse<GetUserTableResponseDto> result = adminTableQueryRepository.getUserTableData(dto);

        return Response.ok(result);
    }

    /**
     * 관리자 페이지의 게시글 데이터 조회
     * 페이징과 필터링이 적용된 게시글 목록 반환
     *
     * @param dto 게시글 테이블 조회를 위한 요청 파라미터
     *            (페이지 번호, 페이지 크기, 검색 조건 등을 포함)
     * @return Response<GetTableListResponse<GetPostTableResponseDto>>
     *         - 성공 시: 페이징된 게시글 목록과 전체 데이터 수가 포함된 응답
     *         - 실패 시: 적절한 HTTP 상태 코드와 에러 메시지
     */
    @GetMapping("/posts")
    public Response<GetTableListResponse<GetPostTableResponseDto>> posts(
            GetPostTableRequestDto dto) {
        GetTableListResponse<GetPostTableResponseDto> result = adminTableQueryRepository.getPostTableData(dto);

        return Response.ok(result);
    }

    /**
     * 관리자 페이지의 댓글 데이터 조회
     * 페이징과 필터링이 적용된 댓글 목록 반환
     *
     * @param dto 댓글 테이블 조회를 위한 요청 파라미터
     *            (페이지 번호, 페이지 크기, 검색 조건 등을 포함)
     * @return Response<GetTableListResponse<GetCommentTableResponseDto>>
     *         - 성공 시: 페이징된 댓글 목록과 전체 데이터 수가 포함된 응답
     *         - 실패 시: 적절한 HTTP 상태 코드와 에러 메시지
     */
    @GetMapping("/comments")
    public Response<GetTableListResponse<GetCommentTableResponseDto>> comments(
            GetCommentTableRequestDto dto) {
        GetTableListResponse<GetCommentTableResponseDto> result = adminTableQueryRepository.getCommentTableData(dto);

        return Response.ok(result);
    }
}
