package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface TaskRepository extends MongoRepository<Task, UUID> {

}
