package com.ndayal.my_hiit.controller;

import com.ndayal.my_hiit.service.WorkoutService;
import com.ndayal.my_hiit.vo.WorkoutListVO;
import com.ndayal.my_hiit.vo.WorkoutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

    // Get all workouts/user defined workouts
    @GetMapping("/workout")
    public ResponseEntity<WorkoutListVO> getWorkout(@RequestParam(value = "user_id", required = false) String userId, @RequestParam(value = "trending", required = false) String trending) {
        return ResponseEntity.ok(workoutService.getWorkout(userId, trending));
    }

    // Add General workout/Add User defined workout
    @PostMapping("/workout")
    public ResponseEntity<WorkoutVO> addWorkout(@RequestBody WorkoutVO workoutVO) {
        return ResponseEntity.ok(workoutService.addWorkout(workoutVO));
    }

    // Update Name/Exercise List, Add User to Workout user list
    // TODO: Ideally we should pass the complete list of users but the payload size might go out of bounds
    @PutMapping("/workout/{id}")
    public ResponseEntity<WorkoutVO> updateWorkout(@RequestBody WorkoutVO workoutVO, @PathVariable(value = "id") Long id, @RequestParam(name = "type", required = false) String type) {
        return ResponseEntity.ok(workoutService.updateWorkout(workoutVO, id,type));
    }

    // Delete Workout by Id
    @DeleteMapping("/workout/{id}")
    public ResponseEntity<Boolean> deleteWorkout(@PathVariable(value = "id") Long id) {
        workoutService.deleteWorkout(id);

        return ResponseEntity.ok(Boolean.TRUE);
    }
}
