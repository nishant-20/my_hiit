package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.service.WorkoutService;
import com.ndayal.my_hiit.vo.WorkoutListVO;
import com.ndayal.my_hiit.vo.WorkoutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

    @GetMapping("/workouts")
    public ResponseEntity<WorkoutListVO> getWorkout(@RequestParam(value = "user_id", required = false) String userId) {
        return ResponseEntity.ok(workoutService.getWorkout(userId));
    }

    @PostMapping("/workouts")
    public ResponseEntity<WorkoutVO> addWorkout(@RequestBody WorkoutVO workoutVO) {
        return ResponseEntity.ok(workoutService.addWorkout(workoutVO));
    }

    @PutMapping("/workouts/{id}")
    public ResponseEntity<WorkoutVO> updateWorkout(@RequestBody WorkoutVO workoutVO, @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(workoutService.updateWorkout(workoutVO, id));
    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<Boolean> deleteWorkout(@PathVariable(value = "id") Long id) {
        workoutService.deleteWorkout(id);

        return ResponseEntity.ok(Boolean.TRUE);
    }
}
