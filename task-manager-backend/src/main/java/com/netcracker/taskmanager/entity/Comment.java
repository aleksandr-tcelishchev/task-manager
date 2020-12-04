package com.netcracker.taskmanager.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document
@Getter
@Setter
public class Comment {
    @Id
    private String id;
    private ZonedDateTime creationTime;
    private String message;
    private CommentFile file;
}
