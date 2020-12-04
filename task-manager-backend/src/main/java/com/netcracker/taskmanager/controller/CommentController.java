package com.netcracker.taskmanager.controller;

import com.netcracker.taskmanager.entity.Comment;
import com.netcracker.taskmanager.entity.CommentForm;
import com.netcracker.taskmanager.service.CommentService;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> findAllComments(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findCommentById(@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.findCommentById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/for-task/{id}")
    public ResponseEntity findCommentsForTaskById(@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.findCommentForTask(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity addCommentForTaskById(@PathVariable String id, CommentForm commentForm){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(id,commentForm.getFile(),commentForm.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity editCommentById(@PathVariable String id,@RequestBody String message){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id,message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCommentById(@PathVariable String id){
        try{
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
