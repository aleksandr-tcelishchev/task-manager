package com.netcracker.taskmanager.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;


@Getter
@Setter
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private List<Task> taskList;

    public User(){}
}
