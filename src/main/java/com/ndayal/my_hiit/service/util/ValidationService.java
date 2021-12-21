package com.ndayal.my_hiit.service.util;

import com.ndayal.my_hiit.dao.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    public boolean checkUserExists(String userId) {
        if(!validateUserId(userId))
            return false;

        boolean doesUserExists = userRepository.existsById(Long.parseLong(userId));

        return doesUserExists;
    }

    public boolean validateUserId(String userId) {
        Long parsedUserId = Long.parseLong(userId);

        // If the userId is out of bounds
        if (parsedUserId <= 0 || parsedUserId > Long.MAX_VALUE) {
            logger.info("UserId out of bounds, returning empty response");
            return false;
        }

        return true;
    }
}
