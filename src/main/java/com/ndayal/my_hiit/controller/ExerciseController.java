package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.dto.Exercise;
import com.ndayal.my_hiit.service.ExerciseService;
import com.ndayal.my_hiit.vo.ExerciseListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @GetMapping(value = "/exercise", produces = {"application/json"})
    public ResponseEntity<ExerciseListVO> getExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }
}
