package com.app.flashgram.post.application;

import com.app.flashgram.fake.FakeObjectFactory;
import com.app.flashgram.post.appication.CommentService;
import com.app.flashgram.post.appication.PostService;
import com.app.flashgram.post.appication.dto.CreateCommentRequestDto;
import com.app.flashgram.post.appication.dto.CreatePostRequestDto;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.post.domain.content.PostPublicationState;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.application.dto.CreateUserRequestDto;
import com.app.flashgram.user.domain.User;

public class PostApplicationTestTemplate {

    final UserService userService = FakeObjectFactory.getUserService();
    final PostService postService = FakeObjectFactory.getPostService();
    final CommentService commentService = FakeObjectFactory.getCommentService();

    final User user = userService.createUser(new CreateUserRequestDto("user1", null));
    final User otherUser = userService.createUser(new CreateUserRequestDto("user1", null));

    CreatePostRequestDto postRequestDto = new CreatePostRequestDto(user.getId(), "this is test content",
            PostPublicationState.PUBLIC);

    final Post post = postService.createPost(postRequestDto);

    final String commentContentText = "this is test comment";
    CreateCommentRequestDto commentRequestDto = new CreateCommentRequestDto(post.getId(),
            user.getId(), "this is test comment");
}
