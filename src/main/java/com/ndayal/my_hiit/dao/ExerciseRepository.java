package com.ndayal.my_hiit.dao;

import com.ndayal.my_hiit.dto.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    public List<Exercise> findAllByOrderByNameAsc();
}
