package com.netcracker.taskmanager.service;

import com.netcracker.taskmanager.entity.User;
import com.netcracker.taskmanager.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user){
        if(isUnique(user)){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }else{
            throw new RuntimeException("this username already registered");
        }
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }



    public User editUser(User user){
        User oldUser = findUserById(user.getId());
        if(user.getUsername() != null) oldUser.setUsername(user.getUsername());
        if(user.getPassword() != null) oldUser.setPassword(user.getPassword());
        return userRepository.save(oldUser);
    }

    public void deleteUser(String id){
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User findUserByUsername(String username){
        Optional<User> userFromDb = userRepository.findUserByUsername(username);
        if(userFromDb.isPresent()){
            return userFromDb.get();
        }else{
            throw new UsernameNotFoundException("cant find user with username : " + username);
        }
    }

    public User findUserByUsernameAndPassword(String username,String password){
        try {
            User user = findUserByUsername(username);
            if(passwordEncoder.matches(password,user.getPassword())) return user;
        }catch (Exception e){
            throw new UsernameNotFoundException("cant login with : " + username + " - " + password);
        }
        return null;
    }

    public User findUserById(String id){
        Optional<User> userFromDb = userRepository.findUserById(id);
        if(userFromDb.isPresent()){
            return userFromDb.get();
        }else{
            throw new UsernameNotFoundException("cant find user with id : " + id);
        }
    }

    public boolean isUnique(User user){
        for(User u : userRepository.findAll()){
            if(u.getUsername().equals(user.getUsername())) return false;
        }
        return true;
    }
}
