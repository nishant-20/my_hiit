package com.ndayal.my_hiit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ndayal.my_hiit.dao.UserRepository;
import com.ndayal.my_hiit.dao.WorkoutHistoryRepository;
import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.dto.WorkoutHistory;
import com.ndayal.my_hiit.service.WorkoutHistoryService;
import com.ndayal.my_hiit.service.util.WorkoutServiceUtil;
import com.ndayal.my_hiit.vo.ExerciseVO;
import com.ndayal.my_hiit.vo.UserVO;
import com.ndayal.my_hiit.vo.WorkoutHistoryListVO;
import com.ndayal.my_hiit.vo.WorkoutHistoryVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutHistoryServiceImpl implements WorkoutHistoryService {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    WorkoutHistoryRepository workoutHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutServiceUtil workoutServiceUtil;

    @Override
    public WorkoutHistoryListVO getWorkoutHistories(String userId) {
        WorkoutHistoryListVO workoutHistoryListVO = new WorkoutHistoryListVO();
        List<WorkoutHistoryVO> workoutHistoryVOList = new ArrayList<>();

        if(StringUtils.hasLength(userId)) {
            boolean userExists = userRepository.existsById(Long.parseLong(userId));

            if(!userExists) {
                logger.error(String.format("User with id:%s doesn't exist. Returning empty history.", userId));
                return workoutHistoryListVO;
            }

            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();

            List<WorkoutHistory> workoutHistories = workoutHistoryRepository.findAllByUserOrderByCreatedTimeDesc(user);

            workoutHistoryVOList = workoutHistories.stream().map(workoutHistory -> {
                List<ExerciseVO> exerciseVOS = workoutServiceUtil.descriptionToExercises(workoutHistory.getDescription());
                Long totalDuration = workoutServiceUtil.getExercisesTotalDuration(workoutHistory.getDescription());

                WorkoutHistoryVO workoutHistoryVO = new WorkoutHistoryVO(workoutHistory.getName(), exerciseVOS, totalDuration, workoutHistory.getCreatedTime());

                return workoutHistoryVO;
            }).collect(Collectors.toList());
        } else {
            List<WorkoutHistory> workoutHistories = workoutHistoryRepository.findAllByOrderByCreatedTimeDesc();

            workoutHistoryVOList = workoutHistories.stream().map(workoutHistory -> {
                List<ExerciseVO> exerciseVOS = workoutServiceUtil.descriptionToExercises(workoutHistory.getDescription());
                UserVO userVO = new UserVO(workoutHistory.getUser().getId(), workoutHistory.getUser().getName());
                Long totalDuration = workoutServiceUtil.getExercisesTotalDuration(workoutHistory.getDescription());

                WorkoutHistoryVO workoutHistoryVO = new WorkoutHistoryVO(workoutHistory.getName(), exerciseVOS, totalDuration, workoutHistory.getCreatedTime(), userVO);

                return workoutHistoryVO;
            }).collect(Collectors.toList());
        }

        workoutHistoryListVO.setWorkoutHistoryVOList(workoutHistoryVOList);

        return workoutHistoryListVO;
    }

    @Override
    public WorkoutHistoryVO addWorkoutHistory(WorkoutHistoryVO workoutHistoryVO, String userId) {
        WorkoutHistory workoutHistory = new WorkoutHistory();
        WorkoutHistoryVO updatedWorkoutHistoryVO = new WorkoutHistoryVO();

        boolean userExists = userRepository.existsById(Long.parseLong(userId));

        if(!userExists) {
            logger.error(String.format("User with id:%s doesn't exist", userId));
            return workoutHistoryVO;
        }

        try {
            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();
            workoutHistory = new WorkoutHistory(user, workoutHistoryVO.getName(), workoutServiceUtil.exercisesToDescription(workoutHistoryVO.getExercises()));
        } catch(JsonProcessingException ex) {
            logger.error("Exception occurred while creating workoutHistory entity");
            return workoutHistoryVO;
        }

        logger.info(String.format("Saving workout history for user:%s", userId));
        workoutHistory = workoutHistoryRepository.save(workoutHistory);
        logger.info(String.format("Workout history with id:%s for user:%s saved successfully.", workoutHistory.getId(), userId));

        UserVO userVO = new UserVO(workoutHistory.getUser().getId(), workoutHistory.getUser().getName());

        updatedWorkoutHistoryVO = new WorkoutHistoryVO(workoutHistory.getName(), workoutServiceUtil.descriptionToExercises(workoutHistory.getDescription()), workoutHistory.getCreatedTime(), userVO);

        return updatedWorkoutHistoryVO;
    }
}
