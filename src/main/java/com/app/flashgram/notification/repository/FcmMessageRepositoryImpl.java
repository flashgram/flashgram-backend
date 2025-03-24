package com.app.flashgram.notification.repository;

import com.app.flashgram.notification.application.interfaces.MessageRepository;
import com.app.flashgram.notification.repository.entity.FcmTokenEntity;
import com.app.flashgram.user.domain.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message; // 수정된 부분
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Firebase Cloud Messaging을 통한 메시지 전송을 담당하는 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class FcmMessageRepositoryImpl implements MessageRepository {

    private final JpaFcmTokenRepository jpaFcmTokenRepository;

    private static final String LIKE_MESSAGE_TEMPLATE = "%s님이 %s님의 글에 좋아요를 눌렀습니다.";
    private static final String MESSGE_KEY = "message";

    /**
     * 좋아요 알림 메시지 전송
     *
     * @param sender 좋아요를 누른 유저
     * @param targetUser 좋아요를 받은 글의 작성자
     */
    @Override
    public void sendLikeMessage(User sender, User targetUser) {
        Optional<FcmTokenEntity> tokenEntity = jpaFcmTokenRepository.findById(targetUser.getId());

        if (tokenEntity.isEmpty()) {

            return;
        }

        FcmTokenEntity token = tokenEntity.get();
        Message message = Message.builder()
                .putData(MESSGE_KEY, LIKE_MESSAGE_TEMPLATE.formatted(sender.getName(), targetUser.getName()))
                .setToken(token.getFcmToken())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
