package com.ndayal.my_hiit.service;

import com.ndayal.my_hiit.vo.WorkoutListVO;
import com.ndayal.my_hiit.vo.WorkoutVO;

public interface WorkoutService {
    public WorkoutVO addWorkout(WorkoutVO workoutVO);
    public WorkoutVO updateWorkout(WorkoutVO workoutVO, long id, String type);
    public WorkoutListVO getWorkout(String userId, String trending);
    public void deleteWorkout(Long workoutId);
}
