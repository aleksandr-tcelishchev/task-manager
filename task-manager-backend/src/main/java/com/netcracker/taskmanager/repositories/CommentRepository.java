package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
    public Optional<Comment> findCommentById(String id);
}
