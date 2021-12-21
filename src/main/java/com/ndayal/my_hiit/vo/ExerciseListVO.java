package com.ndayal.my_hiit.vo;

import com.ndayal.my_hiit.dto.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseListVO {
    private List<Exercise> exercises;

    @Override
    public String toString() {
        return "ExerciseListVO{" +
                "exercises=" + exercises +
                '}';
    }
}
