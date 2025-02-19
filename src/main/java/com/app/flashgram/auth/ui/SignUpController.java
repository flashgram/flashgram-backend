package com.app.flashgram.auth.ui;

import com.app.flashgram.auth.appliction.AuthService;
import com.app.flashgram.auth.appliction.EmailService;
import com.app.flashgram.auth.appliction.dto.CreateUserAuthRequestDto;
import com.app.flashgram.auth.appliction.dto.SendEmailRequestDto;
import com.app.flashgram.common.ui.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원가입 관련 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final EmailService emailService;
    private final AuthService authService;

    /**
     * 회원가입 인증 이메일 전송
     *
     * @param dto 이메일 전송에 필요한 정보를 담은 DTO
     * @return Response 객체. 성공 시 data null
     */
    @PostMapping("/send-verification-email")
    public Response<Void> sendEmail(@RequestBody SendEmailRequestDto dto) {
        emailService.sendEmail(dto);

        return Response.ok(null);
    }

    /**
     * 이메일 인증 토큰 검증
     *
     * @param email 인증할 이메일 주소
     * @param token 이메일 인증을 위한 토큰
     * @return Response 객체
     */
    @GetMapping("/verify-token")
    public Response<Void> verifyEmail(String email, String token) {
        emailService.verifyEmail(email, token);

        return Response.ok(null);
    }

    /**
     * 회원가입 요청을 처리하고 유저를 등록
     *
     * @param dto 회원가입에 필요한 정보를 담은 DTO
     * @return Response 객체. 성공 시 등록된 유저 ID 반환
     */
    @PostMapping("/register")
    public Response<Long> register(@RequestBody CreateUserAuthRequestDto dto) {
        return Response.ok(authService.registerUser(dto));
    }
}
