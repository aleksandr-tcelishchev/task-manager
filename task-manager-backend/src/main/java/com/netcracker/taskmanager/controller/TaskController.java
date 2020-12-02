package com.netcracker.taskmanager.controller;

import com.netcracker.taskmanager.entity.Task;
import com.netcracker.taskmanager.service.TaskService;
import com.netcracker.taskmanager.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    //TODO: авторизация и проверка пользователя

    @GetMapping("/task/{task}")
    @ResponseBody
    public Task getTask(@PathVariable Task task) {
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        return task;
    }

    @GetMapping("/tasks")
    @ResponseBody
    public List<Task> getTasks(@RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "dd_MM_yyyy") LocalDate from,
                               @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "dd_MM_yyyy") LocalDate to) {
        ZonedDateTime fromZoned = null;
        ZonedDateTime toZoned = null;
        if (from != null) fromZoned = from.atStartOfDay(ZoneId.systemDefault());
        if (to != null) toZoned = to.atStartOfDay(ZoneId.systemDefault());
        return taskService.findAllDateTime(fromZoned, toZoned);
    }

    //временная реализация без валидации
    @PostMapping("/task/create")
    @ResponseBody
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }
}
