package com.ndayal.my_hiit.dao;

import com.ndayal.my_hiit.dto.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    public List<Workout> findAllByUserId(Long userId);
}
