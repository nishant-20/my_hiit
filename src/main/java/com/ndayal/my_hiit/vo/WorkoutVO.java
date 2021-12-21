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
    private UserVO user;
    private List<ExerciseVO> exercises;

    public WorkoutVO(Long id, String name, Long totalDuration, List<ExerciseVO> exercises) {
        this.id = id;
        this.name = name;
        this.totalDuration = totalDuration;
        this.exercises = exercises;
    }

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
