package com.app.flashgram.post.appication;

import com.app.flashgram.post.appication.dto.CreatePostRequestDto;
import com.app.flashgram.post.appication.dto.LikeRequestDto;
import com.app.flashgram.post.appication.dto.UpdatePostRequestDto;
import com.app.flashgram.post.appication.interfaces.CommentCommandRepository;
import com.app.flashgram.post.appication.interfaces.LikeRepository;
import com.app.flashgram.post.appication.interfaces.PostRepository;
import com.app.flashgram.post.domain.Post;
import com.app.flashgram.user.application.UserService;
import com.app.flashgram.user.domain.User;
import org.springframework.stereotype.Service;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
public class PostService {

    private final UserService userService;

    private final PostRepository postRepository;
    private final CommentCommandRepository commentCommandRepository;
    private final LikeRepository likeRepository;

    public PostService(UserService userService, PostRepository postRepository, CommentCommandRepository commentCommandRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.commentCommandRepository = commentCommandRepository;
        this.likeRepository = likeRepository;
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

    public void deletePost(Long postId) {

        likeRepository.unlikeAllByPost(postId);
        likeRepository.unlikeAllByCommentsOfPost(postId);
        commentCommandRepository.deleteAllByPost(postId);
        postRepository.delete(postId);
    }

    /**
     * 게시글에 좋아요 추가
     * 이미 좋아요를 누른 경우 아무 동작도 수행하지 않음
     *
     * @param dto 좋아요 요청 정보
     */
    public void likePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(post, user)) {
            return;
        }

        post.like(user);
        likeRepository.like(post, user);
    }

    /**
     * 게시글의 좋아요 취소
     * 좋아요를 누르지 않은 경우 아무 동작도 수행하지 않음
     *
     * @param dto 좋아요 요청 정보
     */
    public void unlikePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(post, user)) {
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }
}
