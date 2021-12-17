package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value= "/v1")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> addUsers(@RequestBody User user) {
        User res = userService.addUser(user);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
