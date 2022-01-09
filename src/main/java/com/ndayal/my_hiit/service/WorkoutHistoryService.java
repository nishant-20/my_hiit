package com.ndayal.my_hiit.service;

import com.ndayal.my_hiit.vo.WorkoutHistoryListVO;
import com.ndayal.my_hiit.vo.WorkoutHistoryVO;

public interface WorkoutHistoryService {
    public WorkoutHistoryListVO getWorkoutHistories(String userId);
    public WorkoutHistoryVO addWorkoutHistory(WorkoutHistoryVO workoutHistoryVO, String userId);
}
