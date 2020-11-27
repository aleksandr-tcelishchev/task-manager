package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {

}
