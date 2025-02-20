package com.app.flashgram.auth.appliction;

import com.app.flashgram.auth.appliction.dto.CreateUserAuthRequestDto;
import com.app.flashgram.auth.appliction.dto.LoginRequestDto;
import com.app.flashgram.auth.appliction.dto.UserAccessTokenResponseDto;
import com.app.flashgram.auth.appliction.interfaces.EmailVerificationRepository;
import com.app.flashgram.auth.appliction.interfaces.UserAuthRepository;
import com.app.flashgram.auth.domain.Email;
import com.app.flashgram.auth.domain.TokenProvider;
import com.app.flashgram.auth.domain.UserAuth;
import com.app.flashgram.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 유저 인증 및 회원가입을 처리하는 서비스 클래스
 * 이메일 인증 및 유저 등록 프로세스 관리
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final TokenProvider tokenProvider;

    /**
     * 유저 등록 메소드
     *
     * @param dto 유저 등록에 필요한 정보를 담고 있는 DTO 객체
     * @return 등록된 유저의 ID
     * @throws IllegalArgumentException 이메일이 인증되지 않은 경우 발생
     */
    public  Long registerUser(CreateUserAuthRequestDto dto){
        Email email = Email.createEmail(dto.email());

        if (!emailVerificationRepository.isEmailVerified(email)) {
            throw new IllegalArgumentException("인증되지 않은 이메일입니다.");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImgUrl());
        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }

    public UserAccessTokenResponseDto login(LoginRequestDto dto){
        UserAuth userAuth = userAuthRepository.loginUser(dto.email(), dto.password());
        String token = tokenProvider.createToken(userAuth.getUserId(), userAuth.getUserRole());

        return new UserAccessTokenResponseDto(token);
    }
}
