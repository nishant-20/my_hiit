package com.ndayal.my_hiit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseVO {
    private Long id;
    private String name;
    private Integer duration;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
