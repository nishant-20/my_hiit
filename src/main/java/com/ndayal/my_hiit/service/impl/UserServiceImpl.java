package com.ndayal.my_hiit.service.impl;

import com.ndayal.my_hiit.dao.UserRepository;
import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.service.UserService;
import com.ndayal.my_hiit.vo.UserListVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    // Storing the user email in lowercase
    @Override
    public User addUser(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }

    @Override
    public UserListVO getUser(String email) {
        UserListVO userListVO = new UserListVO();

        List<User> users = new ArrayList<User>();

        if(StringUtils.hasLength(email)) {
            logger.info(String.format("Received email=%s, fetching user for the mentioned email", email));

            email = email.toLowerCase();
            users = userRepository.findByEmail(email);
        } else {
            logger.info("No email passed in the request, fetching all users");

            users = userRepository.findAll();
        }

        userListVO.setUsers(users);
        return userListVO;
    }
}
