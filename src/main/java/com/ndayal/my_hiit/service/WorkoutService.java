package com.ndayal.my_hiit.service;

import com.ndayal.my_hiit.vo.WorkoutListVO;
import com.ndayal.my_hiit.vo.WorkoutVO;

import java.util.List;

public interface WorkoutService {
    public WorkoutVO addWorkout(WorkoutVO workoutVO);
    public WorkoutVO updateWorkout(WorkoutVO workoutVO, long id);
    public WorkoutListVO getWorkout(String userId);
    public void deleteWorkout(Long workoutId);
}
