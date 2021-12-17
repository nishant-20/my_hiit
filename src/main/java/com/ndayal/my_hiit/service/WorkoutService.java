package com.ndayal.my_hiit.service;

import com.ndayal.my_hiit.vo.WorkoutVO;

import java.util.List;

public interface WorkoutService {
    public WorkoutVO addWorkout(WorkoutVO workoutVO);
    public List<WorkoutVO> getWorkout();
}
