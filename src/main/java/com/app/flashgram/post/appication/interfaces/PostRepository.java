package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.Post;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findById(Long id);
}
