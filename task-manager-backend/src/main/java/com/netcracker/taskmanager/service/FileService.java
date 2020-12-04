package com.netcracker.taskmanager.service;

import com.netcracker.taskmanager.entity.CommentFile;
import com.netcracker.taskmanager.repositories.FileRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public CommentFile saveFile(MultipartFile file) throws IOException {
        CommentFile commentFile = new CommentFile();
        commentFile.setData(new Binary(
                BsonBinarySubType.BINARY,
                file.getBytes()
        ));
        return fileRepository.insert(commentFile);
    }

    public void deleteFile(String id){
        CommentFile file = fileRepository.getCommentFileById(id);
        fileRepository.delete(file);
    }
}
