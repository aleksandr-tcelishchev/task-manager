package com.netcracker.taskmanager.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Project {
    @Id
    private String id;
    private String name;
    private String description;
    private Set<User> users = new HashSet<>();
    private Set<Task> tasks = new HashSet<>();
    private Boolean active = true;
}
