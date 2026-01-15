package com.security.scheduling.dao;

import com.security.scheduling.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPostDao implements PostDao {

    @Override
    public List<Post> findAll() {
        return List.of();
    }

    @Override
    public List<Post> findActivePosts() {
        return List.of();
    }

    @Override
    public Post findById(int postId) {
        return null;
    }

    @Override
    public Post create(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public boolean delete(int postId) {
        return false;
    }

    @Override
    public boolean deactivate(int postId) {
        return false;
    }
}
