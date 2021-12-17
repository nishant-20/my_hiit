package com.ndayal.my_hiit.service.impl;

import com.ndayal.my_hiit.dao.ExerciseRepository;
import com.ndayal.my_hiit.dto.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements com.ndayal.my_hiit.service.ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAllByOrderByNameAsc();
    }
}
