package com.netcracker.taskmanager.repositories;

import com.netcracker.taskmanager.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserById(String id);

    Optional<User> findUserByUsername(String username);

}
