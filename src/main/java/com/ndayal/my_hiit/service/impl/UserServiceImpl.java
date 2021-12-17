package com.ndayal.my_hiit.service.impl;

import com.ndayal.my_hiit.dao.UserRepository;
import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
