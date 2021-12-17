package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.service.WorkoutService;
import com.ndayal.my_hiit.vo.WorkoutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

    @GetMapping("/workouts")
    public ResponseEntity<List<WorkoutVO>> getWorkout() {
        return ResponseEntity.ok(workoutService.getWorkout());
    }
}
