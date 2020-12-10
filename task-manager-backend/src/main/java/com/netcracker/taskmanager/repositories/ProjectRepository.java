package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
