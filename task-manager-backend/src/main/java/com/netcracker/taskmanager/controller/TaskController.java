package com.netcracker.taskmanager.controller;

import com.netcracker.taskmanager.entity.Task;
import com.netcracker.taskmanager.service.TaskService;
import com.netcracker.taskmanager.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TaskController {


    private final TaskService taskService;

    public TaskController( TaskService taskService) {
        this.taskService = taskService;
    }

    //TODO: авторизация и проверка пользователя

    @GetMapping("/task/{id}")
    @ResponseBody
    public Task getTask(@PathVariable String id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        Task task = taskService.findById(id);
        if(task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Task with id : " + id + " not found");
        return task;
    }

    @DeleteMapping("/task{id}")
    public ResponseEntity deleteTask(@PathVariable String id,Principal user){
        try{
            taskService.deleteTask(id,user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/task/{id}/complete")
    public ResponseEntity completeTask(@PathVariable String id){
        try {
            return ResponseEntity.ok(taskService.completeTask(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
    public Task createTask(@RequestBody Task task,Principal user) {
        return taskService.save(task,user);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity updateTask(@RequestBody Task task,Principal user,@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(task,user,id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/task/{id}/subtask")
    public ResponseEntity addSubtask(@PathVariable String id,Principal user,@RequestBody Task task){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.addSubtask(id,user,task));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
