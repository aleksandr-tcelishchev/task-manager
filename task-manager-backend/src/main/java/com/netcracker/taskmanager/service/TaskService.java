package com.netcracker.taskmanager.service;

import com.netcracker.taskmanager.entity.Task;
import com.netcracker.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findAllDateTime(ZonedDateTime from, ZonedDateTime to) {
        if (from != null && to != null) {
            return taskRepository.findAllByExpirationTimeBetween(from, to);
        } else if (from != null) {
            return taskRepository.findAllByExpirationTimeGreaterThanEqual(from);
        } else if (to != null) {
            return taskRepository.findAllByExpirationTimeLessThanEqual(to);
        }
        return findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
