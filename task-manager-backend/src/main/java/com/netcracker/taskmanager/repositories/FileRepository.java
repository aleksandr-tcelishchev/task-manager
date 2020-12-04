package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.CommentFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<CommentFile,String> {
    public CommentFile getCommentFileById(String id);
}
