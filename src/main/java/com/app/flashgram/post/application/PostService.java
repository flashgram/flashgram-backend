package com.app.flashgram.post.application;

import com.app.flashgram.post.application.dto.CreatePostRequestDto;
import com.app.flashgram.post.application.dto.UpdatePostRequestDto;
import com.app.flashgram.post.application.event.PostDeleteEvent;
import com.app.flashgram.post.application.interfaces.PostRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PostService(UserService userService, PostRepository postRepository, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * 게시글 ID로 게시글을 조회
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글
     * @throws IllegalArgumentException 게시글이 존재하지 않는 경우
     */
    public Post getPost(Long id) {
        return postRepository.findById(id);
    }

    /**
     * 새로운 게시글 생성
     *
     * @param dto 게시글 생성 요청 정보
     * @return 생성된 게시글
     */
    public Post createPost(CreatePostRequestDto dto) {
        User author = userService.getUser(dto.userId());
        Post post = Post.createPost(null, author, dto.content(), dto.state());

        return postRepository.save(post);
    }

    /**
     * 기존 게시글 수정
     *
     * @param postId 수정할 게시글 ID
     * @param dto 게시글 수정 요청 정보
     * @return 수정된 게시글
     */
    public Post updatePost(Long postId, UpdatePostRequestDto dto) {
        Post post = getPost(postId);
        User user = userService.getUser(dto.userId());

        post.updatePost(user, dto.content(), dto.state());
        return postRepository.save(post);
    }

    /**
     * 게시글 삭제
     *
     * @param postId 삭제할 게시글 ID
     */
    public void deletePost(Long postId) {

        eventPublisher.publishEvent(new PostDeleteEvent(postId));
        postRepository.delete(postId);
    }
}
