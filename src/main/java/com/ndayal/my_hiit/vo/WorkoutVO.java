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
public class WorkoutVO {
    private Long id;
    private String name;
    private Long totalDuration;
    private List<ExerciseVO> exercises;

    @Override
    public String toString() {
        return "WorkoutVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalDuration=" + totalDuration +
                ", exercises=" + exercises +
                '}';
    }
}
