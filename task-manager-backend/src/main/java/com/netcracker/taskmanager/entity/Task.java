package com.netcracker.taskmanager.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Document
public class Task {
    @Id
    private String id;
    private String title;
    private ZonedDateTime creationTime;
    private ZonedDateTime expirationTime;
    private List<String> tags;
    private List<Task> subTasks;
    private List<Comment> comments;
    private boolean isCompleted;

    private Integer priority;

}
