package com.ndayal.my_hiit.dao;

import com.ndayal.my_hiit.dto.User;
import com.ndayal.my_hiit.dto.WorkoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutHistoryRepository extends JpaRepository<WorkoutHistory, Long> {
    List<WorkoutHistory> findAllByUserOrderByCreatedTimeDesc(User user);
    List<WorkoutHistory> findAllByOrderByCreatedTimeDesc();
}
