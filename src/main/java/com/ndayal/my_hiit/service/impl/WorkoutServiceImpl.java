package com.ndayal.my_hiit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndayal.my_hiit.dao.WorkoutRepository;
import com.ndayal.my_hiit.dto.Workout;
import com.ndayal.my_hiit.service.WorkoutService;
import com.ndayal.my_hiit.vo.ExerciseVO;
import com.ndayal.my_hiit.vo.WorkoutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    @Autowired
    WorkoutRepository workoutRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WorkoutVO addWorkout(WorkoutVO workoutVO) {
        String description = "";
        try {
            description = exercisesToDescription(workoutVO.getExercises());
        } catch(JsonProcessingException exception) {
            System.out.println("There is an error with the exercise list.");
            return null;
        }

        Workout workout = new Workout(workoutVO.getId(), workoutVO.getName(), description);
        return null;
    }

    @Override
    public List<WorkoutVO> getWorkout() {
        List<Workout> workouts = workoutRepository.findAll();

        List<WorkoutVO> workoutVOList = workouts.stream().map(workout -> {
            WorkoutVO workoutVO = new WorkoutVO(workout.getId(),
                    workout.getName(),
                    getExercisesTotalDuration(workout.getDescription()),
                    descriptionToExercises(workout.getDescription())
                    );
            return workoutVO;
        }).collect(Collectors.toList());

        return workoutVOList;
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
            System.out.println("There is an error with the method descriptionToExercises" + e);
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
            System.out.println("There is an error with the method getExercisesTotalDuration" + e);
            return null;
        }

        Integer totalDuration = exerciseVOList.stream().map(exerciseVO -> exerciseVO.getDuration()).reduce(0, Integer::sum);

        return Integer.toUnsignedLong(totalDuration);
    }
}

