package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.service.UserService;
import com.ndayal.my_hiit.vo.UserListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value= "/v1")
public class UserController {
    @Autowired
    UserService userService;

    // Add User only
    @PostMapping("/user")
    public ResponseEntity<User> addUsers(@RequestBody User user) {
        User res = userService.addUser(user);

        return ResponseEntity.ok(res);
    }

    // Get User and associated workouts
    @GetMapping("/user")
    public ResponseEntity<UserListVO> getUser(@RequestParam(value = "email", required = false) String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }
}
