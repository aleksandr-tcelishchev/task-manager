package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.ZonedDateTime;
import java.util.List;


public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByExpirationTimeGreaterThanEqual(ZonedDateTime from);

    List<Task> findAllByExpirationTimeLessThanEqual(ZonedDateTime to);

    List<Task> findAllByExpirationTimeBetween(ZonedDateTime from, ZonedDateTime to);

}
