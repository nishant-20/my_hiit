package com.ndayal.my_hiit.service;

import com.ndayal.my_hiit.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public User addUser(User user);
    public List<User> getAllUsers();
}
