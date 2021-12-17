package com.ndayal.my_hiit.dao;

import com.ndayal.my_hiit.dto.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
