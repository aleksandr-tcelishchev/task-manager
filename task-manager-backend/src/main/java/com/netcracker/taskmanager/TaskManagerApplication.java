package com.netcracker.taskmanager;

import com.netcracker.taskmanager.entity.Task;
import com.netcracker.taskmanager.entity.User;
import com.netcracker.taskmanager.repositories.TaskRepository;
import com.netcracker.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class TaskManagerApplication {


    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }


}
