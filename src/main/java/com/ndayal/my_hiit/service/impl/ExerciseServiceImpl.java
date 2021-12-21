package com.ndayal.my_hiit.service.impl;

import com.ndayal.my_hiit.dao.ExerciseRepository;
import com.ndayal.my_hiit.dto.Exercise;
import com.ndayal.my_hiit.vo.ExerciseListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements com.ndayal.my_hiit.service.ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public ExerciseListVO getAllExercises() {
        List<Exercise> exerciseList= exerciseRepository.findAllByOrderByNameAsc();

        ExerciseListVO exerciseListVO = new ExerciseListVO(exerciseList);

        return exerciseListVO;
    }
}
