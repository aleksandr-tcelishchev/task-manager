package com.netcracker.taskmanager.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentForm {
    String message;
    MultipartFile file;
}
