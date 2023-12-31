package com.example.BackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.entities.User;
import com.example.BackEnd.repositories.UserRepository;
import com.example.BackEnd.services.UserResponse;
import com.example.BackEnd.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get/{username}")
    public User getUser(@PathVariable String username) {
        User user = userService.findByUsername(username);

        if (user != null) {
            return user;
        } else {
            User userq = new User();
            userq.setUsername("Nothing!!");
            userq.setPassword("Nothing!!");
            userq.setEmail("Nothing!!");
            return userq;

        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        User existingUser = userService.findByUsername(user.getUsername());

        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(existingUser.getUsername());
        userResponse.setRole(existingUser.getRole());
        userResponse.setMessage("Login successful.");

        return ResponseEntity.ok(userResponse);
    }

}
