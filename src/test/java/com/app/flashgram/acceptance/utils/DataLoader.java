package com.app.flashgram.acceptance.utils;

import static com.app.flashgram.acceptance.steps.UserAcceptanceSteps.createUser;
import static com.app.flashgram.acceptance.steps.UserAcceptanceSteps.followUser;

import com.app.flashgram.user.application.dto.CreateUserRequestDto;
import com.app.flashgram.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    public void loadData() {

        CreateUserRequestDto dto = new CreateUserRequestDto("test user", "");
        createUser(dto);
        createUser(dto);
        createUser(dto);

        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }

}
