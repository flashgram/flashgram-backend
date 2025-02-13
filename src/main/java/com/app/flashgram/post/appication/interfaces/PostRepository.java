package com.app.flashgram.post.appication.interfaces;

import com.app.flashgram.post.domain.Post;

public interface PostRepository {

    Post save(Post post);
    Post findById(Long id);
}
