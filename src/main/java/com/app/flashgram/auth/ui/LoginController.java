package com.app.flashgram.auth.ui;

import com.app.flashgram.auth.appliction.AuthService;
import com.app.flashgram.auth.appliction.dto.LoginRequestDto;
import com.app.flashgram.auth.appliction.dto.UserAccessTokenResponseDto;
import com.app.flashgram.common.ui.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 로그인 관련 API를 처리하는 컨트롤러
 * 유저의 로그인 요청을 처리하고, 로그인 성공 시 액세스 토큰을 반환
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "로그인 API", description = "로그인 관련 API입니다.")
public class LoginController {

    private final AuthService authService;

    /**
     * 유저의 로그인 요청을 처리
     *
     * @param dto 로그인 요청에 필요한 정보를 담은 DTO
     * @return 유저의 액세스 토큰을 포함한 응답 객체
     */
    @PostMapping
    @Operation(summary = "유저 로그인", description = "유저의 로그인을 처리합니다.")
    public Response<UserAccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {

        return Response.ok(authService.login(dto));
    }
}
