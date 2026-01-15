
        package com.security.scheduling.dao;

import com.security.scheduling.model.Post;
import java.util.List;


public interface PostDao {

    List<Post> findAll();
    List<Post> findActivePosts();
    Post findById(int postId);
    Post create(Post post);
    Post update(Post post);
    boolean delete(int postId);
    boolean deactivate(int postId);



}