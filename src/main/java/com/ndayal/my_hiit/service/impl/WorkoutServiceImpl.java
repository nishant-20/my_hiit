package com.ndayal.my_hiit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndayal.my_hiit.dao.UserRepository;
import com.ndayal.my_hiit.dao.WorkoutRepository;
import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.dto.Workout;
import com.ndayal.my_hiit.service.WorkoutService;
import com.ndayal.my_hiit.service.util.ValidationService;
import com.ndayal.my_hiit.vo.ExerciseVO;
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

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WorkoutListVO getWorkout(String userId) {
        List<Workout> workouts = new ArrayList<Workout>();
        WorkoutListVO workoutListVO = new WorkoutListVO();

        logger.info("User ID: " + userId);

        if(StringUtils.hasLength(userId)) {
            if (!validationService.checkUserExists(userId)) {
                logger.error("User not found, no associated workouts. Returning empty response");
                workoutListVO.setWorkouts(new ArrayList<WorkoutVO>());
                return workoutListVO;
            }

            logger.info(String.format("Fetching workouts for user associated with userID:%s",userId));
            workouts = workoutRepository.findAllByUserId(Long.parseLong(userId));
        }
        else {
            logger.info("No userId passed, returning all workouts present in the system");
            workouts = workoutRepository.findAll();
        }

        List<WorkoutVO> workoutVOList = workouts.stream().map(workout -> {
            WorkoutVO workoutVO = new WorkoutVO(workout.getId(),
                    workout.getName(),
                    getExercisesTotalDuration(workout.getDescription()),
                    new UserVO(workout.getUser().getId(), workout.getUser().getName()),
                    descriptionToExercises(workout.getDescription())
                    );
            return workoutVO;
        }).collect(Collectors.toList());

        workoutListVO.setWorkouts(workoutVOList);
        return workoutListVO;
    }

    @Override
    public WorkoutVO addWorkout(WorkoutVO workoutVO) {
        String description = "";
        try {
            description = exercisesToDescription(workoutVO.getExercises());
        } catch(JsonProcessingException exception) {
            logger.error("There is an error parsing the exercise list.");
            return null;
        }

        logger.info("Fetching user from DB");

        User user = userRepository.getById(workoutVO.getUser().getId());
        logger.info("User with id:"+ user.getId() + " fetched successfully");

        Workout workout = new Workout(workoutVO.getName(), user, description);

        // Not validating the exercises, since the exercise list is derived from the DB
        workout = workoutRepository.save(workout);
        logger.info("Workout saved successfully");

        WorkoutVO updatedWorkoutVO = new WorkoutVO(workout.getId(),
                workout.getName(),
                getExercisesTotalDuration(workout.getDescription()),
                new UserVO(user.getId(),user.getName()),
                descriptionToExercises(workout.getDescription())
        );

        return updatedWorkoutVO;
    }

    @Override
    public WorkoutVO updateWorkout(WorkoutVO workoutVO, long id) {
        WorkoutVO updatedWorkoutVO = new WorkoutVO();
        String description = "";
        boolean workoutExists = false;

        workoutExists = workoutRepository.existsById(id);
        if(!workoutExists) {
            logger.error("No workout routine found for the Id:"+id);
            return updatedWorkoutVO;
        }

        try {
            description = exercisesToDescription(workoutVO.getExercises());
        } catch(JsonProcessingException exception) {
            logger.error("There is an error parsing the exercise list.");
            return null;
        }

        logger.info("Fetching user from DB");

        User user = userRepository.getById(workoutVO.getUser().getId());
        logger.info("User with id:"+ user.getId() + " fetched successfully");

        Workout workout = workoutRepository.findById(id).orElseThrow();
        workout.setName(workoutVO.getName());
        workout.setUser(user);
        workout.setDescription(description);

        // Not validating the exercises, since the exercise list is derived from the DB
        workoutRepository.save(workout);
        logger.info("Workout updated successfully");

        updatedWorkoutVO = new WorkoutVO(workout.getId(),
                workout.getName(),
                getExercisesTotalDuration(workout.getDescription()),
                new UserVO(user.getId(),user.getName()),
                descriptionToExercises(workout.getDescription())
        );

        return updatedWorkoutVO;
    }

    @Override
    public void deleteWorkout(Long id) {
        logger.info(String.format("Deleting workout with id:%s", id));
        workoutRepository.deleteById(id);
        logger.info(String.format("Deleted workout successfully with id:%s", id));
    }

    String exercisesToDescription(List<ExerciseVO> exerciseVOS) throws JsonProcessingException {
        String exercisesJSONString = objectMapper.writeValueAsString(exerciseVOS);

        return exercisesJSONString;
    }

    List<ExerciseVO> descriptionToExercises(String description) {
        List<ExerciseVO> exerciseVOList;
        try {
            exerciseVOList = objectMapper.readValue(description, new TypeReference<List<ExerciseVO>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("There is an error with the method descriptionToExercises" + e);
            return null;
        }

        return exerciseVOList;
    }

    Long getExercisesTotalDuration(String description) {
        List<ExerciseVO> exerciseVOList;
        try {
            exerciseVOList = objectMapper.readValue(description, new TypeReference<List<ExerciseVO>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("There is an error with the method getExercisesTotalDuration" + e);
            return null;
        }

        Integer totalDuration = exerciseVOList.stream().map(exerciseVO -> exerciseVO.getDuration()).reduce(0, Integer::sum);

        return Integer.toUnsignedLong(totalDuration);
    }
}

