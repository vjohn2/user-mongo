package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "Welcome to Spring Boot!";
    }

    //create
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) throws URISyntaxException {
    	if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        logger.info("***Creating a new user***"+ user);
        User userSaved = userService.createUser(user);
        return ResponseEntity.created(new URI(userSaved.getId().toString())).body(userSaved); //201
    }

    //read all
    @GetMapping("/users")
    public List<User> getAllUsers(){
        logger.info("***Getting all users***");
        return userService.getAllUsers();
    }

    //read by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        logger.info("***Get User by id***"+id);
        Optional<User> userFound = userService.findUserById(id);
        if(userFound.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(userFound.get());
    }
}
