package com.example.taxpayer.api.controller;


import com.example.taxpayer.repository.UserRepository;
import com.example.taxpayer.service.UserService;
import com.example.taxpayer.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private  UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam Long id) {
        Optional<User> user = userService.getUser(id);
        if(user.isPresent()) {
            return (User) user.get();
        }
return null;
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
