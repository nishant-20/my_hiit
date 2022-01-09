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
    private boolean trending;
    private List<UserVO> users;
    private Long totalDuration;
    private List<ExerciseVO> exercises;

    public WorkoutVO(Long id, String name, boolean trending, Long totalDuration, List<ExerciseVO> exercises) {
        this.id = id;
        this.name = name;
        this.trending = trending;
        this.totalDuration = totalDuration;
        this.exercises = exercises;
    }

    public WorkoutVO(Long id, String name, boolean trending, Long totalDuration, List<ExerciseVO> exercises, List<UserVO> users) {
        this.id = id;
        this.name = name;
        this.trending = trending;
        this.totalDuration = totalDuration;
        this.exercises = exercises;
        this.users = users;
    }

    @Override
    public String toString() {
        return "WorkoutVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trending=" + trending +
                ", totalDuration=" + totalDuration +
                ", exercises=" + exercises +
                '}';
    }
}
