package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.service.WorkoutHistoryService;
import com.ndayal.my_hiit.vo.WorkoutHistoryListVO;
import com.ndayal.my_hiit.vo.WorkoutHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class WorkoutHistoryController {
    @Autowired
    WorkoutHistoryService workoutHistoryService;

    @GetMapping("/workoutHistories")
    public ResponseEntity<WorkoutHistoryListVO> getWorkoutHistories(@RequestParam(name = "user_id", required = false) String userId) {
        return ResponseEntity.ok(workoutHistoryService.getWorkoutHistories(userId));
    }

    @PostMapping("/workoutHistories")
    public ResponseEntity<WorkoutHistoryVO> addWorkoutHistory(@RequestParam(name = "user_id") String userId, @RequestBody WorkoutHistoryVO workoutHistoryVO) {
        return ResponseEntity.ok(workoutHistoryService.addWorkoutHistory(workoutHistoryVO, userId));
    }
}
