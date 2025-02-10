package com.app.flashgram.fake;

import com.app.flashgram.post.appication.PostService;
import com.app.flashgram.post.appication.interfaces.LikeRepository;
import com.app.flashgram.post.appication.interfaces.PostRepository;
import com.app.flashgram.post.repository.FakePostRepository;
import com.app.flashgram.user.application.UserRelationService;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.application.interfaces.UserRelationRepository;
import com.app.flashgram.user.application.interfaces.UserRepository;
import com.app.flashgram.post.repository.FakeLikeRepository;
import com.app.flashgram.user.repository.FakeUserRelationRepository;
import com.app.flashgram.user.repository.FakeUserRepository;

public class FakeObjectFactory {

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();

    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(userService, fakeUserRelationRepository);
    private static final PostService postService = new PostService(userService, fakePostRepository, fakeLikeRepository);

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
}