package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TaskRepository extends MongoRepository<Task, String> {

}
