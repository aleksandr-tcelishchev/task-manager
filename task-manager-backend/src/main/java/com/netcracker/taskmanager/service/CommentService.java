package com.netcracker.taskmanager.service;

import com.netcracker.taskmanager.entity.Comment;
import com.netcracker.taskmanager.entity.Task;
import com.netcracker.taskmanager.repositories.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    FileService fileService;
    CommentRepository commentRepository;
    TaskService taskService;

    public CommentService(FileService fileService, CommentRepository commentRepository, TaskService taskService) {
        this.fileService = fileService;
        this.commentRepository = commentRepository;
        this.taskService = taskService;
    }

    public Comment saveComment(String taskId, MultipartFile file, String message, Principal user) throws IOException {
        Comment comment = new Comment();
        comment.setCreationTime(ZonedDateTime.now());
        comment.setMessage(message);
        if(file != null){
            comment.setFile(
                    fileService.saveFile(file)
            );
        }
        Task task = taskService.findById(taskId);
        Comment commentFromDb =  commentRepository.save(comment);
        if(task.getComments() != null){
            task.getComments().add(commentFromDb);
        }else {
            List<Comment> list = new ArrayList<>();
            list.add(commentFromDb);
            task.setComments(list);
        }
        taskService.save(task,user);
        return commentFromDb;
    }

    public List<Comment> findCommentForTask(String taskId){
        Task task = taskService.findById(taskId);
        if(task != null){
            return task.getComments();
        }else{
            throw new RuntimeException("cant find comment with id : " + taskId);
        }
    }

    public void deleteComment(String id){
        Comment comment = findCommentById(id);
        fileService.deleteFile(comment.getFile().getId());
        commentRepository.delete(comment);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Comment updateComment(String id,String newMessage){
        Comment comment = findCommentById(id);
        comment.setMessage(newMessage);
        return commentRepository.save(comment);
    }

    public Comment findCommentById(String id){
        Optional<Comment> comment = commentRepository.findCommentById(id);
        if(comment.isPresent()){
            return comment.get();
        }else{
            throw new RuntimeException("cant find comment with id : " + id);
        }
    }

}
