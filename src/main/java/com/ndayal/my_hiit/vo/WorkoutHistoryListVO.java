package com.ndayal.my_hiit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutHistoryListVO {
    List<WorkoutHistoryVO> workoutHistoryVOList;

    @Override
    public String toString() {
        return "WorkoutHistoryListVO{" +
                "workoutHistoryVOList=" + workoutHistoryVOList +
                '}';
    }
}
