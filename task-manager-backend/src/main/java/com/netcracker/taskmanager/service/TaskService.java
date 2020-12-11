package com.netcracker.taskmanager.service;

import com.netcracker.taskmanager.entity.Task;
import com.netcracker.taskmanager.entity.User;
import com.netcracker.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public void deleteTask(String id,Principal userlogin){
        User user = userService.findUserByUsername(userlogin.getName());
        Task task = findById(id);
        if(task == null) throw new RuntimeException("cant find task with id : " + id);
        user.getTaskList().remove(task);
        userService.saveUser(user);
        taskRepository.delete(task);
    }

    public Task completeTask(String id){
        Task task =  findById(id);
        if(task == null) throw new RuntimeException("cant find task with id : " + id);
        task.setCompleted(true);
        return taskRepository.save(task);
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

    public Task save(Task task, Principal user) {
        User userFromBd = userService.findUserByUsername(user.getName());
        if(userFromBd.getTaskList() == null) userFromBd.setTaskList(new ArrayList<>());
        task.setCompleted(false);
        Task taskFromDb = taskRepository.save(task);
        userFromBd.getTaskList().add(taskFromDb);
        userService.saveUser(userFromBd);
        return taskFromDb;
    }

    public Task updateTask(Task task,Principal user,String id){ //update title tags and expDate
        User userFromBd = userService.findUserByUsername(user.getName());
        Task taskFromDb = findById(id);
        if(task == null) throw new RuntimeException("cant find task with id : " + id);
        if(task.getTitle() != null )taskFromDb.setTitle(task.getTitle());
        if(task.getTags() != null) taskFromDb.setTags(task.getTags());
        if(task.getExpirationTime() != null) taskFromDb.setExpirationTime(task.getExpirationTime());
        userFromBd.getTaskList().remove(taskFromDb);
        Task taskFromDbNew = taskRepository.save(task);
        userFromBd.getTaskList().add(taskFromDbNew);
        userService.saveUser(userFromBd);
        return taskFromDbNew;
    }


    public Task findById(String id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task addSubtask(String id, Principal username, Task task) {
        Task rootTask = findById(id);
        if(rootTask == null) throw new RuntimeException("cant find task with id : " + id);
        task.setCompleted(false);
        Task taskFromDb = taskRepository.save(task);
        if(rootTask.getSubTasks() == null) rootTask.setSubTasks(new ArrayList<>());
        rootTask.getSubTasks().add(taskFromDb);
        return taskRepository.save(rootTask);
    }
}
