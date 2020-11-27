package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

}
