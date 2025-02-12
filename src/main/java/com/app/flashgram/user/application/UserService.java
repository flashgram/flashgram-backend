package com.app.flashgram.user.application;

import com.app.flashgram.user.application.dto.CreateUserRequestDto;
import com.app.flashgram.user.application.dto.GetUserResponseDto;
import com.app.flashgram.user.application.interfaces.UserRepository;
import com.app.flashgram.user.domain.User;
import com.app.flashgram.user.domain.UserInfo;
import org.springframework.stereotype.Service;

/**
 * 유저 관련 로직을 처리 서비스 클래스
 * 유저 생성 및 조회 기능
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 새로운 유저를 생성 및 저장
     *
     * @param dto 유저 정보 DTO 객체
     * @return 생성된 유저 엔티티
     */
    public User createUser(CreateUserRequestDto dto) {
        UserInfo info = new UserInfo(dto.name(), dto.profileImg());
        User user = new User(null, info);

        return userRepository.save(user);
    }

    public GetUserResponseDto getUserProfile(Long id) {
        User user = getUser(id);

        return new GetUserResponseDto(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }
}
