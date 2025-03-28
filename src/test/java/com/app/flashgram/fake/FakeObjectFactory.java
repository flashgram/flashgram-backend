package com.app.flashgram.fake;

import com.app.flashgram.auth.appliction.interfaces.EmailSendRepository;
import com.app.flashgram.auth.repository.FakeEmailSendRepositoryImpl;
import com.app.flashgram.post.application.CommentService;
import com.app.flashgram.post.application.LikeService;
import com.app.flashgram.post.application.PostService;
import com.app.flashgram.post.application.event.PostDeleteEvent;
import com.app.flashgram.post.application.event.CommentDeleteEvent;
import com.app.flashgram.post.application.event.CommentDeleteEventListener;
import com.app.flashgram.post.application.event.PostDeleteEventListener;
import com.app.flashgram.post.application.interfaces.CommentCommandRepository;
import com.app.flashgram.post.application.interfaces.LikeRepository;
import com.app.flashgram.post.application.interfaces.PostRepository;
import com.app.flashgram.post.repository.FakeCommentCommandRepository;
import com.app.flashgram.post.repository.FakeLikeRepository;
import com.app.flashgram.post.repository.FakePostRepository;
import com.app.flashgram.user.application.UserRelationService;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.application.interfaces.UserRelationRepository;
import com.app.flashgram.user.application.interfaces.UserRepository;
import com.app.flashgram.user.repository.FakeUserRelationRepository;
import com.app.flashgram.user.repository.FakeUserRepository;
import org.springframework.context.ApplicationEventPublisher;

public class FakeObjectFactory {

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();

    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();
    private static final CommentCommandRepository FAKE_COMMENT_COMMAND_REPOSITORY = new FakeCommentCommandRepository();
    private static final EmailSendRepository fakeEmailSendRepository = new FakeEmailSendRepositoryImpl();

    private static final ApplicationEventPublisher fakeEventPublisher = new FakeApplicationEventPublisher();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(userService, fakeUserRelationRepository);
    private static final PostService postService = new PostService(userService, fakePostRepository, fakeEventPublisher);
    private static final CommentService commentService = new CommentService(userService, postService,
            FAKE_COMMENT_COMMAND_REPOSITORY, fakeEventPublisher);
    private static final LikeService likeService = new LikeService(userService, postService, commentService, fakeLikeRepository);

    private static final PostDeleteEventListener postDeleteEventListener = new PostDeleteEventListener(FAKE_COMMENT_COMMAND_REPOSITORY, fakeLikeRepository);
    private static final CommentDeleteEventListener commentDeleteEventListener = new CommentDeleteEventListener(fakeLikeRepository);

    static {
        // FakeApplicationEventPublisher에 이벤트 리스너 등록
        ((FakeApplicationEventPublisher) fakeEventPublisher).registerListener(PostDeleteEvent.class, postDeleteEventListener::handlePostDeleteEvent);
        ((FakeApplicationEventPublisher) fakeEventPublisher).registerListener(CommentDeleteEvent.class, commentDeleteEventListener::handleCommentDeleteEvent);
    }

    private FakeObjectFactory() {}

    public static UserService getUserService() {
        return userService;
    }

    public static UserRelationService getUserRelationService() {
        return userRelationService;
    }

    public static PostService getPostService() {
        return postService;
    }

    public static CommentService getCommentService() {
        return commentService;
    }

    public static LikeService getLikeService() {
        return likeService;
    }

    public static LikeRepository getFakeLikeRepository() {
        return fakeLikeRepository;
    }

    public static EmailSendRepository getEmailSendRepository() {
        return fakeEmailSendRepository;
    }
}