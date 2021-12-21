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
public class WorkoutListVO {
    private List<WorkoutVO> workouts;

    @Override
    public String toString() {
        return "WorkoutListVO{" +
                "workouts=" + workouts +
                '}';
    }
}
