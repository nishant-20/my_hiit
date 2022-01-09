package com.ndayal.my_hiit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ndayal.my_hiit.dao.UserRepository;
import com.ndayal.my_hiit.dao.WorkoutRepository;
import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.dto.Workout;
import com.ndayal.my_hiit.service.WorkoutService;
import com.ndayal.my_hiit.service.util.ValidationService;
import com.ndayal.my_hiit.service.util.WorkoutServiceUtil;
import com.ndayal.my_hiit.vo.UserVO;
import com.ndayal.my_hiit.vo.WorkoutListVO;
import com.ndayal.my_hiit.vo.WorkoutVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidationService validationService;

    @Autowired
    WorkoutServiceUtil workoutServiceUtil;

    @Override
    public WorkoutListVO getWorkout(String userId, String trending) {
        List<Workout> workouts = new ArrayList<Workout>();
        WorkoutListVO workoutListVO = new WorkoutListVO();

        if(StringUtils.hasLength(userId)) {
            logger.info("User ID:" + userId);

            if (!validationService.checkUserExists(userId)) {
                logger.error("User not found, no associated workouts. Returning empty response");
                workoutListVO.setWorkouts(new ArrayList<WorkoutVO>());
                return workoutListVO;
            }

            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();
            logger.info(String.format("Fetching workouts for user associated with userID:%s",userId));
            workouts = workoutRepository.findAllByUsers(user);

            if(StringUtils.hasLength(trending)) {
                if(Boolean.parseBoolean(trending)) {
                    workouts = workouts.stream().filter(workout -> workout.isTrending()).collect(Collectors.toList());
                } else if(!Boolean.parseBoolean(trending)) {
                    workouts = workouts.stream().filter(workout -> !workout.isTrending()).collect(Collectors.toList());
                }
            }

            workoutListVO = createWorkoutListForUser(workouts, user);
        }
        else {
            logger.info("No userId passed, returning all workouts present in the system");
            workouts = workoutRepository.findAll();

            if(Boolean.parseBoolean(trending)) {
                workouts = workouts.stream().filter(workout -> workout.isTrending()).collect(Collectors.toList());
            } else if(!Boolean.parseBoolean(trending)) {
                workouts = workouts.stream().filter(workout -> !workout.isTrending()).collect(Collectors.toList());
            }

            workoutListVO = createWorkoutList(workouts);
        }

        return workoutListVO;
    }

    private WorkoutListVO createWorkoutListForUser(List<Workout> workouts, User user) {
        WorkoutListVO workoutListVO = new WorkoutListVO();

        UserVO userVO = new UserVO(user.getId(), user.getName());
        List<UserVO> userVOS = new ArrayList<>();
        userVOS.add(userVO);

        List<WorkoutVO> workoutVOList = workouts.stream().map(workout -> {
            WorkoutVO workoutVO = new WorkoutVO(workout.getId(), workout.getName(), workout.isTrending(), workoutServiceUtil.getExercisesTotalDuration(workout.getDescription()),
                    workoutServiceUtil.descriptionToExercises(workout.getDescription()), userVOS);

            return workoutVO;
        }).collect(Collectors.toList());

        workoutListVO.setWorkouts(workoutVOList);

        return workoutListVO;
    }

    private WorkoutListVO createWorkoutList(List<Workout> workouts) {
        WorkoutListVO workoutListVO = new WorkoutListVO();

        List<WorkoutVO> workoutVOList = workouts.stream().map(workout -> {
            WorkoutVO workoutVO = new WorkoutVO(workout.getId(), workout.getName(), workout.isTrending(), workoutServiceUtil.getExercisesTotalDuration(workout.getDescription()),
                    workoutServiceUtil.descriptionToExercises(workout.getDescription()));

            return workoutVO;
        }).collect(Collectors.toList());

        workoutListVO.setWorkouts(workoutVOList);

        return workoutListVO;
    }

    @Override
    public WorkoutVO addWorkout(WorkoutVO workoutVO) {
        Workout workout;
        String description = "";
        try {
            description = workoutServiceUtil.exercisesToDescription(workoutVO.getExercises());
        } catch(JsonProcessingException exception) {
            logger.error("There is an error parsing the exercise list.");
            return null;
        }

        // TODO: Check for duplicate workout name or change the schema
        if(workoutVO.getUsers() == null) {
            // Saving general workout
            logger.info("No user attached to the workout, saving as a general workout");
            workout = new Workout(workoutVO.getName(), description, workoutVO.isTrending());
        } else {
            // Saving user defined workout
            logger.info("Fetching user from DB");
            User user = userRepository.getById(workoutVO.getUsers().get(0).getId());
            logger.info("User with id:" + user.getId() + " fetched successfully");

            workout = new Workout(workoutVO.getName(), description, workoutVO.isTrending());
            workout.addUser(user);
        }

        // Not validating the exercises, since the exercise list is derived from the DB
        workout = workoutRepository.save(workout);
        logger.info("Workout saved successfully");

        List<UserVO> users = new ArrayList<>();

        if(workout.getUsers() != null) {
            users = workout.getUsers().stream().map(user -> {
                    UserVO userVO = new UserVO(user.getId(), user.getName());
                    return userVO;
                }).collect(Collectors.toList());
        }

        WorkoutVO updatedWorkoutVO = new WorkoutVO(workout.getId(), workout.getName(), workout.isTrending(), workoutServiceUtil.getExercisesTotalDuration(workout.getDescription()),
                workoutServiceUtil.descriptionToExercises(workout.getDescription()), users);

        return updatedWorkoutVO;
    }

    @Override
    public WorkoutVO updateWorkout(WorkoutVO workoutVO, long id, String type) {
        WorkoutVO updatedWorkoutVO = new WorkoutVO();
        String description = "";
        Long userId = -1L;
        boolean workoutExists = false;

        workoutExists = workoutRepository.existsById(id);
        if(!workoutExists) {
            logger.error("No workout routine found for the Id:"+id);
            return updatedWorkoutVO;
        }

        try {
            description = workoutServiceUtil.exercisesToDescription(workoutVO.getExercises());
        } catch(JsonProcessingException exception) {
            logger.error("There is an error parsing the exercise list.");
            return null;
        }

        Workout workout = workoutRepository.findById(id).orElseThrow();
        workout.setName(workoutVO.getName());
        workout.setDescription(description);

        // Add/Remove user to/from workout user list
        if(StringUtils.hasLength(type) && workoutVO.getUsers() != null) {
            // type = addUser/removeUser
            if(type.equalsIgnoreCase("addUser")) {
                logger.info("Request to add user to workout user list");

                userId = workoutVO.getUsers().get(0).getId();

                if(!validationService.isUserAssociatedToWorkout(id, userId)) {
                    logger.info(String.format("Fetching user with id:%s", userId));
                    User user = userRepository.getById(userId);
                    workout.getUsers().add(user);
                } else {
                    logger.info(String.format("User with id:%s already associated to workout with id:%s", userId, id));
                }
            } else {
                logger.info("Request to remove user from workout user list");

                userId = workoutVO.getUsers().get(0).getId();

                if(validationService.isUserAssociatedToWorkout(id, userId)) {
                    logger.info(String.format("Fetching user with id:%s", userId));
                    User user = userRepository.getById(userId);
                    workout.getUsers().remove(user);
                } else {
                    logger.info(String.format("User with id:%s not associated to workout with id:%s", userId, id));
                }
            }
        }

        // Not validating the exercises, since the exercise list is derived from the DB
        workoutRepository.save(workout);
        logger.info("Workout updated successfully");

        if (workoutVO.getUsers() != null) {
            List<UserVO> userVOList = workout.getUsers().stream().map(user -> {
                    UserVO userVO = new UserVO(user.getId(), user.getName());
                    return userVO;
                }).collect(Collectors.toList());

            updatedWorkoutVO = new WorkoutVO(workout.getId(), workout.getName(), workout.isTrending(), workoutServiceUtil.getExercisesTotalDuration(workout.getDescription()), workoutServiceUtil.descriptionToExercises(workout.getDescription()), userVOList);
        } else {
            updatedWorkoutVO = new WorkoutVO(workout.getId(), workout.getName(), workout.isTrending(), workoutServiceUtil.getExercisesTotalDuration(workout.getDescription()), workoutServiceUtil.descriptionToExercises(workout.getDescription()));
        }

        return updatedWorkoutVO;
    }

    @Override
    public void deleteWorkout(Long id) {
        logger.info(String.format("Deleting workout with id:%s", id));
        workoutRepository.deleteById(id);
        logger.info(String.format("Deleted workout successfully with id:%s", id));
    }
}

