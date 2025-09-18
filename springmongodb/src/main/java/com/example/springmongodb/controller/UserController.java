package com.example.springmongodb.controller;

import com.example.springmongodb.entity.User;
import com.example.springmongodb.repo.UserRepo;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepo userRepo;

    public UserController(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId){
       Optional<User> user=userRepo.findById(userId);
        return user.orElse(null);
    }

    @PostMapping
    public User saveUser(@RequestBody User user){
        user.setUserId(UUID.randomUUID().toString());
        return userRepo.save(user);
   }



}
