package com.ndayal.my_hiit.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndayal.my_hiit.vo.ExerciseVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutServiceUtil {
    Logger logger = LogManager.getLogger(getClass());

    ObjectMapper objectMapper = new ObjectMapper();

    public String exercisesToDescription(List<ExerciseVO> exerciseVOS) throws JsonProcessingException {
        String exercisesJSONString = objectMapper.writeValueAsString(exerciseVOS);

        return exercisesJSONString;
    }

    public List<ExerciseVO> descriptionToExercises(String description) {
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

    public Long getExercisesTotalDuration(String description) {
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
