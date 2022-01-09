package com.ndayal.my_hiit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutHistoryVO {
    private String name;
    private List<ExerciseVO> exercises;
    private Long totalDuration;
    private Date createdTime;
    private UserVO user;

    public WorkoutHistoryVO(String name, List<ExerciseVO> exercises, Date createdTime, UserVO userVO) {
        this.name = name;
        this.exercises = exercises;
        this.createdTime = createdTime;
        this.user = userVO;
    }

    public WorkoutHistoryVO(String name, List<ExerciseVO> exercises, Long totalDuration, Date createdTime) {
        this.name = name;
        this.exercises = exercises;
        this.totalDuration = totalDuration;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "WorkoutHistoryVO{" +
                "name='" + name + '\'' +
                ", exercises=" + exercises +
                ", totalDuration=" + totalDuration +
                ", createdTime=" + createdTime +
                ", user=" + user +
                '}';
    }
}
