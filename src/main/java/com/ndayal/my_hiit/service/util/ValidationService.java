package com.ndayal.my_hiit.service.util;

import com.ndayal.my_hiit.dao.UserRepository;
import com.ndayal.my_hiit.dao.WorkoutRepository;
import com.ndayal.my_hiit.dto.Workout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidationService {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutRepository workoutRepository;

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

    public boolean isUserAssociatedToWorkout(Long workoutId, Long userId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();

        List<Long> userIds = workout.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList());

        return userIds.contains(userId);
    }
}
