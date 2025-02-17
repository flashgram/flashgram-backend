package com.app.flashgram.auth.ui;

import com.app.flashgram.auth.appliction.EmailService;
import com.app.flashgram.auth.appliction.dto.SendEmailRequestDto;
import com.app.flashgram.common.ui.Response;
import lombok.RequiredArgsConstructor;
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

    public final EmailService emailService;

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
}
